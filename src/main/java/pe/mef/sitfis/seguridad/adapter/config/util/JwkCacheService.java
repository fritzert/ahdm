package pe.mef.sitfis.seguridad.adapter.config.util;

import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class JwkCacheService {

  private final WebClient webClient = WebClient.create();
  private final ConcurrentHashMap<String, RSAPublicKey> keyCache = new ConcurrentHashMap<>();

  @Value("${spring.security.oauth2.client.provider.sitfis-webapp.jwk-set-uri}")
  private String jwksUri;

  //Obtiene y cachea claves públicas desde Keycloak
  public Mono<RSAPublicKey> getPublicKey(String kid) {
    if (keyCache.containsKey(kid)) {
      return Mono.just(keyCache.get(kid));
    }

    return webClient.get()
        .uri(jwksUri)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {
        })
        .flatMap(jwks -> {
          Object keysObj = jwks.get("keys");

          if (!(keysObj instanceof List<?> rawKeys)) {
            return Mono.error(new RuntimeException("La clave 'keys' no es una lista válida"));
          }

          for (Object item : rawKeys) {
            if (item instanceof Map<?, ?> rawKeyMap) {
              @SuppressWarnings("unchecked")
              Map<String, Object> key = (Map<String, Object>) rawKeyMap;

              if (kid.equals(key.get("kid"))) {
                try {
                  RSAPublicKey publicKey = RSAKeyUtil.buildRSAPublicKey(
                      (String) key.get("n"),
                      (String) key.get("e")
                  );
                  keyCache.put(kid, publicKey);
                  return Mono.just(publicKey);
                } catch (Exception e) {
                  return Mono.error(
                      new RuntimeException("Error al construir clave pública RSA", e));
                }
              }
            }
          }

          return Mono.error(
              new RuntimeException("No se encontró la clave pública para kid: " + kid));
        });
  }
}

