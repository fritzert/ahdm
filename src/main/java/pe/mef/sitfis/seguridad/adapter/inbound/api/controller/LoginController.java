package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.LoginRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoMenuResponse;
import pe.mef.sitfis.seguridad.application.service.LoginService;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class LoginController {

  private final LoginService loginService;

  @PostMapping("/login")
  public ResponseEntity<SuccessResponse<UsuarioRolGrupoMenuResponse>> login(
      @RequestBody LoginRequest request) {
    var usuarioRolGrupoMenuResponse = loginService.obtenerToken(request.cuenta(), request.clave());
    return SuccessResponseHandler.SUCCESS(usuarioRolGrupoMenuResponse);
  }

  @GetMapping("/logout")
  public ResponseEntity<String> logout() {
    Integer resultado = loginService.logout();
    if (resultado == 1) {
      return ResponseEntity.ok("Sesión cerrada correctamente");
    }
    return null;
  }

}
