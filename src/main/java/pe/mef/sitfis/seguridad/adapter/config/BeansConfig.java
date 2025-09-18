package pe.mef.sitfis.seguridad.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.github.benmanes.caffeine.cache.Caffeine;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import pe.mef.sitfis.seguridad.adapter.config.auditoria.ApplicationAuditorAware;

@Configuration
public class BeansConfig {

  @Bean
  @Primary
  public ObjectMapper objectMapper() {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    JavaTimeModule javaTimeModule = new JavaTimeModule();
    javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(formatter));
    javaTimeModule.addDeserializer(LocalDateTime.class, new LocalDateTimeDeserializer(formatter));

    return Jackson2ObjectMapperBuilder.json()
        .modules(javaTimeModule)
        .timeZone(TimeZone.getTimeZone("America/Lima"))
        .simpleDateFormat("dd/MM/yyyy HH:mm:ss")
        .build();
  }

  @Bean
  public AuditorAware<String> auditorAware() {
    return new ApplicationAuditorAware();
  }

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
