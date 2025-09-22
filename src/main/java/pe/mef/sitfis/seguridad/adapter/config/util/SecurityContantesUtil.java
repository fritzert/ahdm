package pe.mef.sitfis.seguridad.adapter.config.util;

public class SecurityContantesUtil {

  private SecurityContantesUtil() {
    throw new UnsupportedOperationException("Clase Utilitaria");
  }

  public static final String[] PUBLIC_PATHS = {
      "/auth/login/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/v3/api-docs/**",
      "/api-docs/**",
      "/api-docs.yaml",
      "/actuator/**"
  };

  public static final String COOKIE_NAME = "ACCESS_TOKEN";

  public static final String TOKEN_CSRF_NAME = "X-XSRF-TOKEN";

  public static final String ESTADO_KEY = "estado";

  public static final String TOKEN_INVALIDO_FLAG = "true";

}
