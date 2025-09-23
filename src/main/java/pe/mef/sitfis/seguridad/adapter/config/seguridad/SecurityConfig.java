package pe.mef.sitfis.seguridad.adapter.config.seguridad;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static pe.mef.sitfis.seguridad.adapter.config.util.SecurityContantesUtil.PUBLIC_PATHS;

import com.github.benmanes.caffeine.cache.Caffeine;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  private final KeycloakJwtAuthenticationConverter jwtAuthConverter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtDecoder jwtDecoder)
      throws Exception {
    http
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers(PUBLIC_PATHS).permitAll();
          auth.anyRequest().authenticated();
        })
//        .oauth2ResourceServer(oauth2 -> oauth2
//            .jwt(jwt -> jwt.decoder(jwtDecoder)
//                .jwtAuthenticationConverter(jwtAuthConverter)))
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter)))
        .cors(AbstractHttpConfigurer::disable)
        .csrf(AbstractHttpConfigurer::disable)
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

    return http.build();
  }

  /*
  @Bean
  public JwtDecoder jwtDecoder(
      @Value("${spring.security.oauth2.client.provider.sitfis-webapp.jwk-set-uri}") String jwkSetUri) {
    Cache jwkCache = new CaffeineCache("jwkCache",
        Caffeine.newBuilder()
            // Las claves se guardarán en cache por 10 minutos
            .expireAfterWrite(10, java.util.concurrent.TimeUnit.MINUTES)
            .build());
    return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).cache(jwkCache).build();
  }
  */

  @Bean
  public JwtDecoder jwtDecoder(
      @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}") String issuerUri) {
    Cache jwkCache = new CaffeineCache("jwkCache",
        Caffeine.newBuilder()
            .expireAfterWrite(10, TimeUnit.MINUTES)
            .maximumSize(100)
            .recordStats()
            .build());

    return NimbusJwtDecoder
        .withIssuerLocation(issuerUri)
        .cache(jwkCache)
        .build();
  }

  @Bean
  public CsrfTokenRepository csrfTokenRepository() {
    CookieCsrfTokenRepository repository = CookieCsrfTokenRepository.withHttpOnlyFalse();
    repository.setCookieName("XSRF-TOKEN");
    repository.setHeaderName("X-XSRF-TOKEN");
    return repository;
  }

//  @Bean
//  public CsrfTokenRepository csrfTokenRepository() {
//    HttpSessionCsrfTokenRepository repository = new HttpSessionCsrfTokenRepository();
//    repository.setHeaderName("X-CSRF-TOKEN");
//    return repository;
//  }


}
