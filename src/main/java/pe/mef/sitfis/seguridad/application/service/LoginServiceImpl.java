package pe.mef.sitfis.seguridad.application.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    String tokenActual = TokenUtils.extractTokenFromCookie();
    if (tokenActual != null && eliminarTokenCookie() != 1) {
      throw new ResourceNotFoundException("No se elimino la seguridad");
    }

    UsuarioResponse usuario = usuarioService.findBySoloUsuario(cuenta)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    
    if(reintentosLoguin <= usuario.intentoFallido())
    {
    	throw new ResourceNotFoundException("No puede acceder porque supero los Reintentos");
    }
   
    LocalDate hoy = LocalDate.now();
    if (usuario.fechaCaducidad()!=null && hoy.isAfter(usuario.fechaCaducidad()))
    {
    	throw new ResourceNotFoundException("No se puede acceder porque caduco la fecha de su usuario ");
    }
    

    String token = generarTokenLogin(usuario.cuenta(), usuario.clave());
    if (token == null) {
      throw new ResourceNotFoundException("No se genero la seguridad");
    }

    generarCookieLogin(token);
    generarCsrfToken();
    return loginRolesMenuService.obtenerMenusPorUsuario(cuenta);
  }

  private void generarCsrfToken() {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs == null) {
      throw new IllegalStateException("No se pudo obtener el contexto HTTP actual.");
    }

    HttpServletRequest request = attrs.getRequest();
    HttpServletResponse response = attrs.getResponse();
    if (response == null) {
      throw new IllegalStateException("No se pudo obtener la petición o respuesta HTTP.");
    }

    CsrfToken csrfToken = csrfTokenRepository.generateToken(request);

    // GUARDAR EN EL REPOSITORIO, CREA LA COOKIE XSRF-TOKEN
    csrfTokenRepository.saveToken(csrfToken, request, response);

    // AGREGADO EXPLICITO PARA ASEGURAR QUE SE ENVIA
    response.setHeader("X-XSRF-TOKEN", csrfToken.getToken());

    log.info("Token CSRF generado: {}", csrfToken.getToken());
    log.info("Token CSRF guardado en cookie y header");
  }


  private String generarTokenLogin(String username, String password) {
    return keycloakClient.crearTokenUsuario(username, password);
  }

  public void generarCookieLogin(String token) {
    ServletRequestAttributes attrs = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
    if (attrs == null) {
      throw new IllegalStateException("No se pudo obtener el contexto HTTP actual.");
    }

    HttpServletResponse response = attrs.getResponse();
    if (response == null) {
      throw new IllegalStateException("No se pudo obtener la respuesta HTTP.");
    }

    long expEpochSeconds = JwtUtils.extractExpiration(token);
    long currentEpochSeconds = System.currentTimeMillis() / 1000;
    long durationSeconds = expEpochSeconds - currentEpochSeconds;

    ResponseCookie cookie = ResponseCookie.from("ACCESS_TOKEN", token)
        .httpOnly(true)
        .secure(false) // true si usas HTTPS
        .path("/")
        .sameSite("Strict")
        .maxAge(durationSeconds)
        .build();
    response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
  }

  @Override
  public Integer logout() {
    return eliminarTokenCookie();
  }

  private Integer eliminarTokenCookie() {
    String token = TokenUtils.extractTokenFromCookie();
    if (token == null) {
      throw new NotFoundException("Token no encontrado en la cookie.");
    }

    var infoRevocacionToken = keycloakClient.revocarTokenKeyCloak(token);
    log.info("keycloakClient.revocarTokenKeyCloak: {}", infoRevocacionToken);
    keycloakClient.invalidartokenRedis(token);
    TokenUtils.deleteTokenCookie();
    return 1;
  }

}
