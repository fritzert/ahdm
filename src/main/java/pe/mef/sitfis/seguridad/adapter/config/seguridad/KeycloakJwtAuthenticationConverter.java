package pe.mef.sitfis.seguridad.adapter.config.seguridad;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.stereotype.Component;

@Component
public class KeycloakJwtAuthenticationConverter implements
    Converter<Jwt, AbstractAuthenticationToken> {

  private final JwtAuthenticationConverter delegate;

  public KeycloakJwtAuthenticationConverter() {
    JwtGrantedAuthoritiesConverter authoritiesConverter = new JwtGrantedAuthoritiesConverter();
    this.delegate = new JwtAuthenticationConverter();
    this.delegate.setPrincipalClaimName("preferred_username");

    // Configurar el conversor de authorities para personalizarlo
    //authoritiesConverter.setAuthoritiesClaimName("roles"); // roles estan en el claim "roles"
    //authoritiesConverter.setAuthorityPrefix("ROLE_");      // prefix para los roles

    this.delegate.setJwtGrantedAuthoritiesConverter(authoritiesConverter);
  }

  @Override
  @NonNull
  public AbstractAuthenticationToken convert(@NonNull Jwt jwt) {
    return delegate.convert(jwt);
  }

}
