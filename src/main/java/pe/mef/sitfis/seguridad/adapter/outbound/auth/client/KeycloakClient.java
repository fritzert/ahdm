package pe.mef.sitfis.seguridad.adapter.outbound.auth.client;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.ACCESS_TOKEN;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.CLIENT_ID;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.CLIENT_SECRET;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.GRANT_TYPE;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.PASSWORD;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.USERNAME;

import com.fasterxml.jackson.databind.JsonNode;
import java.time.Duration;
import java.time.Instant;
import java.util.Collections;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pe.mef.sitfis.seguridad.adapter.config.seguridad.CredencialesInvalidasException;
import pe.mef.sitfis.seguridad.adapter.config.seguridad.KeycloakServiceException;
import pe.mef.sitfis.seguridad.adapter.config.seguridad.TokenRevocationException;
import pe.mef.sitfis.seguridad.adapter.config.util.TokenListaNegraService;
import pe.mef.sitfis.seguridad.adapter.outbound.auth.dto.KeycloakUsuarioResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class KeycloakClient {

  private final WebClient webClient; // WebClient para llamadas no seguras o con token manual
  private final WebClient adminWebClient; // WebClient configurado para el cliente admin
  private final ReactiveOAuth2AuthorizedClientManager authorizedClientManager;
  private final TokenListaNegraService tokenListaNegraService;
  private final JwtDecoder jwtDecoder;

  // Para usuarios normales
  @Value("${keycloak.token-uri}")
  private String tokenUrl;

  @Value("${keycloak.token-revocation-uri}")
  private String tokenUrlRevoke;

  @Value("${spring.security.oauth2.client.registration.keycloak-user.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.keycloak-user.client-secret}")
  private String clientSecret;

  @Value("${keycloak.logout-uri}")
  private String logoutUrl;

  @Value("${keycloak.users-endpoint}")
  private String usersEndpoint;

  public KeycloakClient(
      WebClient.Builder webClientBuilder,
      @Qualifier("keycloakAdminAuthorizedClientManager") ReactiveOAuth2AuthorizedClientManager authorizedClientManager,
      TokenListaNegraService tokenListaNegraService,
      JwtDecoder jwtDecoder) {
    this.webClient = webClientBuilder.build();
    this.authorizedClientManager = authorizedClientManager;
    this.tokenListaNegraService = tokenListaNegraService;
    this.jwtDecoder = jwtDecoder;

    // Configura un WebClient que automáticamente obtiene y adjunta el token de admin
    ServerOAuth2AuthorizedClientExchangeFilterFunction oauth2Client =
        new ServerOAuth2AuthorizedClientExchangeFilterFunction(authorizedClientManager);
    oauth2Client.setDefaultClientRegistrationId("keycloak-admin");
    this.adminWebClient = webClientBuilder.filter(oauth2Client).build();
  }

  /**
   * Obtiene token de service account con permisos de admin Este token tiene roles: view-users,
   * query-users, manage-users
   */
  public String obtenerTokenAdmin() {
    log.info("Obteniendo token de administrador para 'keycloak-admin'...");
    return authorizedClientManager.authorize(
            OAuth2AuthorizeRequest.withClientRegistrationId("keycloak-admin").principal("backend-admin-service").build())
        .map(OAuth2AuthorizedClient::getAccessToken)
        .map(token -> token.getTokenValue())
        .doOnSuccess(token -> log.info("Token de administrador obtenido exitosamente."))
        .doOnError(error -> log.error("Fallo al obtener el token de administrador.", error))
        .block();
  }

  /**
   * Busca usuario usando el token del service account
   */
  public KeycloakUsuarioResponse buscarCuentaUsuario(String cuenta, String tokenAdmin) {
    // El tokenAdmin ya no es necesario, el adminWebClient lo gestiona solo.
    String url = String.format("%s?username=%s", usersEndpoint, cuenta);

    // 🔍 AGREGAR ESTOS LOGS DE DIAGNÓSTICO
    log.info("🔍 DIAGNÓSTICO BÚSQUEDA - URL base: {}", usersEndpoint);
    log.info("🔍 DIAGNÓSTICO BÚSQUEDA - URL completa: {}", url);
    log.info("🔍 DIAGNÓSTICO BÚSQUEDA - Usuario buscado: {}", cuenta);

    log.debug("Buscando usuario: {} en {}", cuenta, url);

    return adminWebClient.get() // Usamos el WebClient configurado para admin
        .uri(url)
        .header(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .retrieve()
        .onStatus(status -> status.value() == 403,
            response -> response.bodyToMono(String.class)
                .flatMap(error -> {
                  log.error("❌ 403 - Service account sin permisos. Error: {}", error);
                  String adminClientId = "backend-admin-service"; // O leer de properties
                  log.error("🔍 Headers enviados:");
                  // El token ya no se maneja manualmente, pero podemos loguear el intento
                  log.error("   - Authorization: Bearer [token gestionado por Spring]");
                  log.error("   - Accept: {}", MediaType.APPLICATION_JSON_VALUE);
                  log.error("   - URL: {}", url);
                  log.error("Asegurar que {} tenga roles: view-users, query-users", adminClientId);
                  return Mono.error(
                      new RuntimeException(
                          "Service account sin permisos (view-users, query-users)"));
                }))
        .onStatus(status -> status.value() == 401,
            response -> response.bodyToMono(String.class)
                .flatMap(error -> {
                  log.error("❌ 401 - Token inválido: {}", error);
                  return Mono.error(
                      new RuntimeException("Token admin inválido"));
                }))
        .onStatus(HttpStatusCode::is5xxServerError,
            response -> Mono.error(
                new RuntimeException("Keycloak no disponible")))
        .bodyToMono(JsonNode.class)
        .mapNotNull(jsonNode -> {
          if (jsonNode.isArray() && !jsonNode.isEmpty()) {
            JsonNode firstUser = jsonNode.get(0);
            log.info("✅ Usuario encontrado: {}", cuenta);
            return new KeycloakUsuarioResponse(
                firstUser.get("username").asText(),
                firstUser.get("firstName").asText(),
                firstUser.get("lastName").asText()
            );
          }
          log.info("Usuario no encontrado en Keycloak: {}", cuenta);
          return null; // Usuario no existe - esto NO es un error
        })
        .block();
  }

  public String crearTokenUsuario(String username, String password) {
    return webClient.post()
        .uri(tokenUrl)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(BodyInserters
            .fromFormData("grant_type", "password")
            .with("username", username)
            .with("password", password)
            .with("client_id", clientId)
            .with("client_secret", clientSecret)
//            .fromFormData(GRANT_TYPE, PASSWORD)
//            .with(USERNAME, username)
//            .with(PASSWORD, password)
//            .with(CLIENT_ID, clientId)
//            .with(CLIENT_SECRET, clientSecret)
        )
        .retrieve()
        .onStatus(HttpStatusCode::isError,
            response -> response.bodyToMono(String.class)
                .flatMap(errorBody -> {
                  log.error("Error al crear token: {}", errorBody);
                  return Mono.error(
                      new CredencialesInvalidasException("Credenciales inválidas"));
                }))
        .bodyToMono(JsonNode.class)
        .map(jsonNode -> jsonNode.get(ACCESS_TOKEN).asText())
        .doOnSuccess(token -> log.info("Token creado exitosamente para usuario: {}", username))
        .block();
  }

  public String revocarTokenKeyCloak(String token) {
    return webClient.post()
        .uri(tokenUrlRevoke)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(BodyInserters
            .fromFormData("token_type_hint", ACCESS_TOKEN)
            .with("token", token)
            .with(CLIENT_ID, clientId)
            .with(CLIENT_SECRET, clientSecret)
        )
        .retrieve()
        .onStatus(HttpStatusCode::isError,
            response -> response.bodyToMono(String.class)
                .flatMap(errorBody -> {
                  log.error("Error al revocar token: {}", errorBody);
                  return Mono.error(
                      new TokenRevocationException("Error al revocar token"));
                }))
        .toBodilessEntity()
        .map(response -> "Token revocado exitosamente")
        .doOnSuccess(result -> log.info("Token revocado exitosamente"))
        .block();
  }

  public void invalidartokenRedis(String token) {
    try {
      Jwt jwt = jwtDecoder.decode(token);

      // Obtener la expiracion del token
      Instant expiration = jwt.getExpiresAt();
      if (expiration == null) {
        log.warn("Token sin fecha de expiración, usando TTL por defecto");
        expiration = Instant.now().plusSeconds(3600); // 1 hora por defecto
      }

      // Calcular tiempo restante
      Duration ttl = Duration.between(Instant.now(), expiration);
      if (ttl.isNegative() || ttl.isZero()) {
        log.info("Token ya expirado, no es necesario invalidarlo");
        return;
      }

      log.info("Añadiendo token a blacklist con TTL: {} segundos", ttl.getSeconds());

      // Guardar en Redis
      tokenListaNegraService.guardarTokenlistaNegraRedis(token, ttl)
          .doOnSuccess(v -> log.info("Token añadido a blacklist exitosamente"))
          .doOnError(
              error -> log.error("Error al añadir token a blacklist: {}", error.getMessage()))
          .subscribe();
    } catch (JwtException e) {
      log.error("Error al decodificar token para invalidación: {}", e.getMessage());
      tokenListaNegraService.guardarTokenlistaNegraRedis(token, Duration.ofHours(1))
          .subscribe();
    }
  }

  public Map<String, Object> obtenerClaimsToken(String token) {
    try {
      Jwt jwt = jwtDecoder.decode(token);
      return jwt.getClaims();
    } catch (JwtException e) {
      log.error("Error al obtener claims del token: {}", e.getMessage());
      return Collections.emptyMap();
    }
  }

}
