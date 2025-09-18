package pe.mef.sitfis.seguridad.adapter.config.seguridad;

import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

  private static final String[] PUBLIC_URLS = {
      "/auth/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/v3/api-docs/**",
      "/api-docs/**",
      "/api-docs.yaml",
      "/actuator/**"
  };

  private final KeycloakJwtAuthenticationConverter jwtAuthConverter;

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, JwtDecoder jwtDecoder)
      throws Exception {
    http
        .authorizeHttpRequests(auth -> {
          auth.requestMatchers(PUBLIC_URLS).permitAll();
          auth.anyRequest().authenticated();
        })
        .csrf(AbstractHttpConfigurer::disable)
        .oauth2ResourceServer(oauth2 -> oauth2
            .jwt(jwt -> jwt.decoder(jwtDecoder)
                .jwtAuthenticationConverter(jwtAuthConverter)))
        .sessionManagement(session -> session.sessionCreationPolicy(STATELESS));

    return http.build();
  }

}
