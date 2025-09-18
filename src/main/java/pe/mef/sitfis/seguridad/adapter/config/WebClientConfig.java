package pe.mef.sitfis.seguridad.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Configuration
public class WebClientConfig {

  private final ObjectMapper objectMapper;

  public WebClientConfig(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Bean
  public WebClient.Builder webClientBuilder() {
    return WebClient.builder()
//        .filter(new ServletBearerExchangeFilterFunction()) // Propaga el token JWT automaticamente
        .filter(createAuthenticationFilter())             // Backup para tokens en cookies
        .exchangeStrategies(ExchangeStrategies.builder()
            .codecs(configurer -> {
              configurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper));
              configurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper));
            })
            .build());
  }

  private ExchangeFilterFunction createAuthenticationFilter() {
    return ExchangeFilterFunction.ofRequestProcessor(clientRequest -> {
      try {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        String token = extractToken(request);
        if (token != null && !token.isEmpty()) {
          ClientRequest newRequest = ClientRequest.from(clientRequest)
              .header("Authorization", "Bearer " + token)
              .build();
          return Mono.just(newRequest);
        }
      } catch (IllegalStateException e) {
        // No hay request actual, ignoramos
      }
      return Mono.just(clientRequest);
    });
  }

  private String extractToken(HttpServletRequest request) {
    // Primero intentamos obtener del header Authorization
    String authHeader = request.getHeader("Authorization");
    if (authHeader != null && authHeader.startsWith("Bearer ")) {
      return authHeader.substring(7);
    }

    // Si no hay header, intentamos con la cookie
    Cookie[] cookies = request.getCookies();
    if (cookies != null) {
      for (Cookie cookie : cookies) {
        if ("ACCESS_TOKEN".equals(cookie.getName())) {
          return cookie.getValue();
        }
      }
    }
    return null;
  }

}