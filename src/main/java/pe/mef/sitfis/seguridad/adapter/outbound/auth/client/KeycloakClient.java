package pe.mef.sitfis.seguridad.adapter.outbound.auth.client;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.ACCESS_TOKEN;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.CLIENT_ID;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.CLIENT_SECRET;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.GRANT_TYPE;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.PASSWORD;
import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.KeycloakUtil.USERNAME;

import com.fasterxml.jackson.databind.JsonNode;
import java.security.interfaces.RSAPublicKey;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import pe.mef.sitfis.seguridad.adapter.config.util.JwkCacheService;
import pe.mef.sitfis.seguridad.adapter.config.util.TokenListaNegraService;
import pe.mef.sitfis.seguridad.adapter.config.util.TokenUtils;
import pe.mef.sitfis.seguridad.adapter.outbound.auth.dto.KeycloakUsuarioResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class KeycloakClient {

  private final WebClient webClient;
  private final TokenListaNegraService tokenListaNegraService;
  private final JwkCacheService jwkCacheService;

  @Value("${spring.security.oauth2.client.provider.sitfis-webapp.token-uri}")
  private String tokenUrl;
  @Value("${keycloak.token-uri_revokeToken}")
  private String tokenUrlRevoke;
  @Value("${spring.security.oauth2.client.registration.sitfis-webapp.client-id}")
  private String clientId;
  @Value("${spring.security.oauth2.client.registration.sitfis-webapp.client-secret}")
  private String clientSecret;
  @Value("${keycloak.logout-uri}")
  private String logoutUrl;

  @Value("${keycloak.master.token-uri}")
  private String masterUrl;
  @Value("${keycloak.master.username}")
  private String masterUsername;
  @Value("${keycloak.master.password}")
  private String masterPassword;
  @Value("${keycloak.user-validate}")
  private String usuarioValidacionUrl;

  public KeycloakClient(WebClient.Builder webClientBuilder,
      TokenListaNegraService tokenListaNegraService,
      JwkCacheService jwkCacheService) {
    this.webClient = webClientBuilder.build();
    this.tokenListaNegraService = tokenListaNegraService;
    this.jwkCacheService = jwkCacheService;
  }

  public String obtenerTokenAdmin() {
    return webClient.post()
        .uri(masterUrl)
        .header("Content-Type", "application/x-www-form-urlencoded")
        .body(BodyInserters
            .fromFormData(GRANT_TYPE, PASSWORD)
            .with(USERNAME, masterUsername)
            .with(PASSWORD, masterPassword)
            .with(CLIENT_ID, "admin-cli"))
        .retrieve()
        .onStatus(
            status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> Mono.error(
                new RuntimeException("Error en la solicitud: " + clientResponse.statusCode()))
        )
        .bodyToMono(JsonNode.class)
        .map(jsonNode -> jsonNode.get(ACCESS_TOKEN).asText())
        .block();
  }

  public KeycloakUsuarioResponse buscarCuentaUsuario(String cuenta, String tokenAdmin) {
    String url = String.format("%s?username=%s", usuarioValidacionUrl, cuenta);

    return webClient.get()
        .uri(url)
        .header("Authorization", "Bearer " + tokenAdmin)
        .retrieve()
        .onStatus(
            status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> Mono.error(
                new RuntimeException("Error en la solicitud: " + clientResponse.statusCode()))
        )
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
          return null;
        })
        .block();
  }

  public String crearTokenUsuario(String username, String password) {
    return webClient.post()
        .uri(tokenUrl)
        .header("Content-Type", "application/x-www-form-urlencoded")
        .body(BodyInserters
            .fromFormData(GRANT_TYPE, PASSWORD)
            .with(USERNAME, username)
            .with(PASSWORD, password)
            .with(CLIENT_ID, clientId)
            .with(CLIENT_SECRET, clientSecret)
        )
        .retrieve()
        .onStatus(
            status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> clientResponse
                .bodyToMono(String.class)
                .map(errorBody -> new RuntimeException("Error al obtener token: " + errorBody))
        )
        .bodyToMono(JsonNode.class)
        .map(jsonNode -> jsonNode.get(ACCESS_TOKEN).asText())
        .block();
  }

  public String revocarTokenKeyCloak(String token) {
    return webClient.post()
        .uri(tokenUrlRevoke)
        .header("Content-Type", "application/x-www-form-urlencoded")
        .body(BodyInserters
            .fromFormData("token_type_hint", ACCESS_TOKEN)
            .with("token", token)
            .with(CLIENT_ID, clientId)
            .with(CLIENT_SECRET, clientSecret)
        )
        .retrieve()
        .onStatus(
            status -> status.is4xxClientError() || status.is5xxServerError(),
            clientResponse -> clientResponse
                .bodyToMono(String.class)
                .map(errorBody -> new RuntimeException("Error al revocar token: " + errorBody))
        )
        .toBodilessEntity()
        .map(response -> "Token revocado exitosamente")
        .block();
  }

  public void invalidartokenRedis(String token) {
    String kid = TokenUtils.extraerKid(token);
    RSAPublicKey publicKey = jwkCacheService.getPublicKey(kid).block();

    long segundosRestantes = TokenUtils.obtenerSegundosRestantes(token, publicKey);
    Duration ttl = Duration.ofSeconds(segundosRestantes);

    log.info("Añadiendo token a blacklist: {}", token);

    tokenListaNegraService.guardarTokenlistaNegraRedis(token, ttl)
        .subscribe(); // dispara operación reactiva
  }


}
