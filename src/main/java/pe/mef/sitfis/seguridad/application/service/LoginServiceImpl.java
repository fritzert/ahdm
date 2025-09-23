package pe.mef.sitfis.seguridad.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import pe.mef.sitfis.seguridad.adapter.config.util.JwtUtils;
import pe.mef.sitfis.seguridad.adapter.config.util.TokenUtils;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoMenuResponse;
import pe.mef.sitfis.seguridad.adapter.outbound.auth.client.KeycloakClient;
import pe.mef.sitfis.seguridad.application.exception.BusinessException;
import pe.mef.sitfis.seguridad.application.exception.NotFoundException;
import pe.mef.sitfis.seguridad.application.exception.ResourceNotFoundException;

@Slf4j
@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

  private final LoginRolesMenuService loginRolesMenuService;
  private final UsuarioService usuarioService;
  private final KeycloakClient keycloakClient;
  private final CsrfTokenRepository csrfTokenRepository;

  @Value("${reintentosLoguin}")
  private int reintentosLoguin;

  @Override
  public UsuarioRolGrupoMenuResponse obtenerToken(String cuenta, String clave) {
    eliminarTokenCookieExistente();
    UsuarioResponse usuario = validarUsuario(cuenta);
    String token = generarYConfigurarToken(usuario.cuenta(), usuario.clave());
    return loginRolesMenuService.obtenerMenusPorUsuario(cuenta);
  }

  /**
   * Elimina el token existente si hay uno en las cookies
   */
  private void eliminarTokenCookieExistente() {
    String tokenActual = TokenUtils.extractTokenFromCookie();
    if (tokenActual != null) {
      try {
        eliminarTokenCookie();
      } catch (Exception e) {
        log.warn("Error al eliminar token existente: {}", e.getMessage());
        // Continuar con el flujo normal
      }
    }
  }

  /**
   * Valida el usuario y sus restricciones de acceso
   */
  private UsuarioResponse validarUsuario(String cuenta) {
    UsuarioResponse usuario = usuarioService.findBySoloUsuario(cuenta)
        .orElseThrow(() -> new NotFoundException("Usuario no existente: " + cuenta));

    // Validar intentos fallidos
    if (usuario.intentoFallido() >= reintentosLoguin) {
      throw new ResourceNotFoundException(
          String.format("Usuario bloqueado. Ha superado los %d intentos permitidos",
              reintentosLoguin)
      );
    }

    validarFechaCaducidad(usuario);
    return usuario;
  }

  /**
   * Valida la fecha de caducidad del usuario
   */
  private void validarFechaCaducidad(UsuarioResponse usuario) {
    if (usuario.fechaCaducidad() == null) {
      return; // Sin fecha de caducidad configurada
    }

    LocalDate hoy = LocalDate.now();
    if (hoy.isAfter(usuario.fechaCaducidad())) {
      throw new ResourceNotFoundException(
          String.format("Usuario caducado. Fecha de vencimiento: %s",
              usuario.fechaCaducidad().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")))
      );
    }
  }

  /**
   * Genera token y configura cookies de seguridad
   */
  private String generarYConfigurarToken(String username, String password) {
    String token = keycloakClient.crearTokenUsuario(username, password);

    if (token == null || token.isBlank()) {
      throw new ResourceNotFoundException("No se pudo generar el token de autenticación");
    }

    generarCookieLogin(token);
    generarCsrfToken();

    log.info("Token generado exitosamente para usuario: {}", username);
    return token;
  }

  /**
   * Genera la cookie de autenticación con el token JWT
   */
  private void generarCookieLogin(String token) {
    HttpServletResponse response = obtenerResponseActual();

    long durationSeconds = calcularDuracionToken(token);

    ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", token)
        .httpOnly(true)
        .secure(false) // Cambiar a true en producción con HTTPS
        .path("/")
        .sameSite("Strict")
        .maxAge(durationSeconds)
        .build();

    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    log.debug("Cookie de autenticación configurada con duración: {} segundos", durationSeconds);
  }

  /**
   * Genera y configura el token CSRF
   */
  private void generarCsrfToken() {
    HttpServletRequest request = obtenerRequestActual();
    HttpServletResponse response = obtenerResponseActual();

    CsrfToken csrfToken = csrfTokenRepository.generateToken(request);
    csrfTokenRepository.saveToken(csrfToken, request, response);
    response.setHeader("X-XSRF-TOKEN", csrfToken.getToken());

    log.debug("Token CSRF generado: {}", csrfToken.getToken());
  }

  /**
   * Calcula la duración del token en segundos
   */
  private long calcularDuracionToken(String token) {
    long expEpochSeconds = JwtUtils.extractExpiration(token);
    long currentEpochSeconds = System.currentTimeMillis() / 1000;
    return Math.max(expEpochSeconds - currentEpochSeconds, 0);
  }

  @Override
  public Integer logout() {
    try {
      String token = TokenUtils.extractTokenFromCookie();

      if (token == null) {
        log.warn("Intento de logout sin token válido");
        throw new NotFoundException("No hay sesión activa para cerrar");
      }

      String resultado = keycloakClient.revocarTokenKeyCloak(token);
      log.info("Token revocado en Keycloak: {}", resultado);

      keycloakClient.invalidartokenRedis(token);

      TokenUtils.deleteTokenCookie();

      log.info("Logout exitoso");
      return 1;

    } catch (NotFoundException e) {
      throw e;
    } catch (Exception e) {
      log.error("Error durante el logout: {}", e.getMessage(), e);
      throw new BusinessException("Error al cerrar sesión: " + e.getMessage());
    }
  }

  /**
   * Método extraído y renombrado para claridad
   */
  private Integer eliminarTokenCookie() {
    return logout();
  }

  /**
   * Obtiene el request actual del contexto
   */
  private HttpServletRequest obtenerRequestActual() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs == null) {
      throw new IllegalStateException("No se pudo obtener el contexto HTTP actual");
    }
    return attrs.getRequest();
  }

  /**
   * Obtiene el response actual del contexto
   */
  private HttpServletResponse obtenerResponseActual() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs == null || attrs.getResponse() == null) {
      throw new IllegalStateException("No se pudo obtener la respuesta HTTP actual");
    }
    return attrs.getResponse();
  }

}
