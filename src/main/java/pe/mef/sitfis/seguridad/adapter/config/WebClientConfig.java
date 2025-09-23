package pe.mef.sitfis.seguridad.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.ReactiveOAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.DefaultReactiveOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.reactive.function.client.ServletBearerExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class WebClientConfig {

  private final ObjectMapper objectMapper;

  @Bean
  @Qualifier("keycloakAdminAuthorizedClientManager")
  public ReactiveOAuth2AuthorizedClientManager keycloakAdminAuthorizedClientManager(
      ReactiveClientRegistrationRepository clientRegistrationRepository,
      ServerOAuth2AuthorizedClientRepository authorizedClientRepository) {

    ReactiveOAuth2AuthorizedClientProvider authorizedClientProvider =
        ReactiveOAuth2AuthorizedClientProviderBuilder.builder()
            .clientCredentials()
            .build();

    DefaultReactiveOAuth2AuthorizedClientManager authorizedClientManager =
        new DefaultReactiveOAuth2AuthorizedClientManager(
            clientRegistrationRepository, authorizedClientRepository);

    authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

    return authorizedClientManager;
  }

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

  /**
   * WebClient pre-configurado para usar en servicios Alternativa: inyectar WebClient.Builder y
   * crear instancias según necesidad
   */
  @Bean
  public WebClient webClient(WebClient.Builder builder) {
    return builder
        .filter(loggingFilter())
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

  /**
   * Filtro alternativo para extraer token de cookies (BACKUP)
   * Útil si el token no está en el SecurityContext pero sí en cookies
   */
  @Bean
  public ExchangeFilterFunction cookieTokenFilter() {
    return (request, next) -> {
      if (request.headers().containsKey(HttpHeaders.AUTHORIZATION)) {
        return next.exchange(request);
      }

      return ReactiveSecurityContextHolder.getContext()
          .map(SecurityContext::getAuthentication)
          .filter(auth -> auth instanceof JwtAuthenticationToken)
          .map(auth -> (JwtAuthenticationToken) auth)
          .map(JwtAuthenticationToken::getToken)
          .map(Jwt::getTokenValue)
          .map(token -> ClientRequest.from(request)
              .headers(headers -> headers.setBearerAuth(token))
              .build())
          .defaultIfEmpty(request)
          .flatMap(next::exchange);
    };
  }

  /*
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
   */


}