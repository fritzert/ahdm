package pe.mef.sitfis.seguridad.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

  private final ObjectMapper objectMapper;

  /**
   * WebClient.Builder para crear instancias de WebClient con configuración común - Propaga
   * automáticamente el token JWT del contexto de seguridad - Configura Jackson para serialización
   * JSON - Establece timeouts y tamaños de buffer
   */
  @Bean
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder()
        .filter(new ServletBearerExchangeFilterFunction()) // Propaga el token JWT automaticamente
//        .filter(createAuthenticationFilter())             // Backup para tokens en cookies
        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> {
              configurer.defaultCodecs().jackson2JsonDecoder(
                  new Jackson2JsonDecoder(objectMapper, MediaType.APPLICATION_JSON));
              configurer.defaultCodecs().jackson2JsonEncoder(
                  new Jackson2JsonEncoder(objectMapper, MediaType.APPLICATION_JSON));

              // Aumentar el tamaño del buffer para respuestas grandes
              //configurer.defaultCodecs().maxInMemorySize(16 * 1024 * 1024); // 16MB
            })
            .build());
  }

//  public WebClientConfig(ObjectMapper objectMapper) {
//    this.objectMapper = objectMapper;
//  }

  /**
   * WebClient pre-configurado para usar en servicios Alternativa: inyectar WebClient.Builder y
   * crear instancias según necesidad
   */
  @Bean
  public WebClient webClient(WebClient.Builder builder) {
    return builder
        .filter(loggingFilter()) // ✅ Agregar logging para debugging
        .build();
  }

  /**
   * Filtro de logging para debugging (opcional) Útil en desarrollo para ver las
   * peticiones/respuestas
   */
  private ExchangeFilterFunction loggingFilter() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      if (log.isDebugEnabled()) {
        log.debug("WebClient Request: {} {}",
            clientRequest.method(),
            clientRequest.url());
        clientRequest.headers().forEach((name, values) ->
            log.debug("Header: {}={}", name, values));
      }
      return Mono.just(clientRequest);
    });
  }

}