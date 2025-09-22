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
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pe.mef.sitfis.seguridad.adapter.config.seguridad.CredencialesInvalidasException;
import pe.mef.sitfis.seguridad.adapter.config.seguridad.KeycloakAuthException;
import pe.mef.sitfis.seguridad.adapter.config.seguridad.KeycloakServiceException;
import pe.mef.sitfis.seguridad.adapter.config.seguridad.TokenRevocationException;
import pe.mef.sitfis.seguridad.adapter.config.seguridad.UsuarioNoEncontradoException;
import pe.mef.sitfis.seguridad.adapter.config.util.TokenListaNegraService;
import pe.mef.sitfis.seguridad.adapter.outbound.auth.dto.KeycloakUsuarioResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class KeycloakClient {

  private final WebClient webClient;
  private final TokenListaNegraService tokenListaNegraService;
  //  private final JwkCacheService jwkCacheService;
  private final JwtDecoder jwtDecoder;

//  @Value("${spring.security.oauth2.client.provider.sitfis-webapp.token-uri}")
//  private String tokenUrl;
//  @Value("${keycloak.token-uri_revokeToken}")
//  private String tokenUrlRevoke;
//  @Value("${spring.security.oauth2.client.registration.sitfis-webapp.client-id}")
//  private String clientId;
//  @Value("${spring.security.oauth2.client.registration.sitfis-webapp.client-secret}")
//  private String clientSecret;
//  @Value("${keycloak.logout-uri}")
//  private String logoutUrl;
//
//  @Value("${keycloak.master.token-uri}")
//  private String masterUrl;
//  @Value("${keycloak.master.username}")
//  private String masterUsername;
//  @Value("${keycloak.master.password}")
//  private String masterPassword;
//  @Value("${keycloak.user-validate}")
//  private String usuarioValidacionUrl;

  @Value("${keycloak.token-uri:#{null}}")
  private String tokenUrl;

  @Value("${keycloak.token-revocation-uri}")
  private String tokenUrlRevoke;

  @Value("${spring.security.oauth2.client.registration.keycloak.client-id}")
  private String clientId;

  @Value("${spring.security.oauth2.client.registration.keycloak.client-secret}")
  private String clientSecret;

  @Value("${keycloak.logout-uri}")
  private String logoutUrl;

  // Admin Keycloak
  @Value("${keycloak.admin.token-uri}")
  private String masterTokenUrl;

  @Value("${keycloak.admin.username}")
  private String masterUsername;

  @Value("${keycloak.admin.password}")
  private String masterPassword;

  @Value("${keycloak.admin.users-endpoint}")
  private String usuarioValidacionUrl;

//  public KeycloakClient(WebClient.Builder webClientBuilder,
//      TokenListaNegraService tokenListaNegraService
////      JwkCacheService jwkCacheService
//  ) {
//    this.webClient = webClientBuilder.build();
//    this.tokenListaNegraService = tokenListaNegraService;
////    this.jwkCacheService = jwkCacheService;
//  }

  public String obtenerTokenAdmin() {
    return webClient.post()
        .uri(masterTokenUrl)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(BodyInserters
            .fromFormData(GRANT_TYPE, PASSWORD)
            .with(USERNAME, masterUsername)
            .with(PASSWORD, masterPassword)
            .with(CLIENT_ID, "admin-cli"))
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError,
            response -> response.bodyToMono(String.class)
                .flatMap(error -> Mono.error(
                    new KeycloakAuthException("Error de autenticación: " + error))))
        .onStatus(HttpStatusCode::is5xxServerError,
            response -> Mono.error(
                new KeycloakServiceException("Keycloak no disponible")))
//        .onStatus(
//            status -> status.is4xxClientError() || status.is5xxServerError(),
//            clientResponse -> Mono.error(
//                new RuntimeException("Error en la solicitud: " + clientResponse.statusCode()))
//        )
        .bodyToMono(JsonNode.class)
        .map(jsonNode -> jsonNode.get(ACCESS_TOKEN).asText())
        .doOnSuccess(token -> log.debug("Token admin obtenido exitosamente"))
        .doOnError(error -> log.error("Error obteniendo token admin: {}", error.getMessage()))
        .block();
  }

  public KeycloakUsuarioResponse buscarCuentaUsuario(String cuenta, String tokenAdmin) {
    String url = String.format("%s?username=%s", usuarioValidacionUrl, cuenta);

    return webClient.get()
        .uri(url)
        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tokenAdmin)
        .retrieve()
        .onStatus(HttpStatusCode::is4xxClientError,
            response -> response.bodyToMono(String.class)
                .flatMap(error -> Mono.error(
                    new UsuarioNoEncontradoException("Usuario no encontrado: " + cuenta))))
        .onStatus(HttpStatusCode::is5xxServerError,
            response -> Mono.error(
                new KeycloakServiceException("Error al buscar usuario")))
        .bodyToMono(JsonNode.class)
        .mapNotNull(jsonNode -> {
          if (jsonNode.isArray() && !jsonNode.isEmpty()) {
            JsonNode firstUser = jsonNode.get(0);
            return new KeycloakUsuarioResponse(
                firstUser.get("username").asText(),
                firstUser.get("firstName").asText(),
                firstUser.get("lastName").asText()
            );
          }
          log.warn("Usuario no encontrado en Keycloak: {}", cuenta);
          return null;
        })
        .block();
  }

  public String crearTokenUsuario(String username, String password) {
    return webClient.post()
        .uri(tokenUrl)
        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE)
        .body(BodyInserters
            .fromFormData(GRANT_TYPE, PASSWORD)
            .with(USERNAME, username)
            .with(PASSWORD, password)
            .with(CLIENT_ID, clientId)
            .with(CLIENT_SECRET, clientSecret)
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

      // Obtener la expiración del token
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
      // Aún así agregarlo a la blacklist con TTL por defecto
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
