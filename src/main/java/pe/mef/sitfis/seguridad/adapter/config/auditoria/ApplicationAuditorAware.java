package pe.mef.sitfis.seguridad.adapter.config.auditoria;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class ApplicationAuditorAware implements AuditorAware<String> {

  @Override
  public Optional<String> getCurrentAuditor() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null || !authentication.isAuthenticated()) {
      return Optional.empty();
    }

    if (authentication.getPrincipal() instanceof Jwt jwt) {
      return Optional.ofNullable(jwt.getClaimAsString("preferred_username"));
    }

    return Optional.empty();
  }
}
