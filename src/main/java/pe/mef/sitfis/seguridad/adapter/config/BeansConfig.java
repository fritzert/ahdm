package pe.mef.sitfis.seguridad.adapter.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.AuditorAware;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
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

}
