package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearUsuarioRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioDisponibleResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioPersonaRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioPersonaResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.application.exception.ResourceNotFoundException;
import pe.mef.sitfis.seguridad.application.exception.ServiceException;
import pe.mef.sitfis.seguridad.application.service.UsuarioService;

@Slf4j
@RestController
@RequestMapping("/api/admin/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

  private final UsuarioService usuarioService;

  @GetMapping()
  public ResponseEntity<Map<String, Object>> findAllUsuariosPaginacion(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size) throws ServiceException {
    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
    Page<UsuarioPersonaResponse> usuarioPage = usuarioService.findAllPaginacion(pageable, page,
        size);

    if (usuarioPage.isEmpty()) {
      throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.toString());
    }

    Map<String, Object> response = new HashMap<>();
    response.put("usuarios", usuarioPage.getContent());
    response.put("currentPage", usuarioPage.getNumber());
    response.put("totalItems", usuarioPage.getTotalElements());
    response.put("totalPages", usuarioPage.getTotalPages());

    return new ResponseEntity<>(response, HttpStatus.OK);
  }


  @GetMapping("/buscarPersonaId")
  public ResponseEntity<SuccessResponse<UUID>> obtenerPersonaId(@AuthenticationPrincipal Jwt jwt) {
    String username = jwt.getClaim("preferred_username");
    UUID resultado = usuarioService.obtenerPersonaId(username);
    return SuccessResponseHandler.SUCCESS(resultado);
  }


  @GetMapping("/sin-roles")
  public ResponseEntity<SuccessResponse<List<UsuarioComboResponse>>> findAllUsuarioSinRoles(
      @RequestParam(value = "usuario", required = false) String usuario) {
    var resultado = usuarioService.findAllUsuarioSinRoles(usuario);
    return SuccessResponseHandler.SUCCESS(resultado);
  }

  @GetMapping("/usuario-rol")
  public ResponseEntity<SuccessResponse<List<UsuarioComboResponse>>> listarTodosUsuarioConRol(
      @RequestParam(value = "usuario", required = false) String usuario) {
    var resultado = usuarioService.listarTodosUsuarioConRol(usuario);
    return SuccessResponseHandler.SUCCESS(resultado);
  }

  @GetMapping("/buscarUsuario")
  public ResponseEntity<Map<String, Object>> findByLikeUsuario(
      @RequestParam(value = "cuenta", required = false) String cuenta,
      @RequestParam(value = "estado", required = false) int estado,
      @RequestParam(value = "nombre", required = false) String nombre,
      @RequestParam(value = "tipoDocumentoId", defaultValue = "-1") Long tipoDocumentoId,
      @RequestParam(value = "numeroDocumento", required = false) String numeroDocumento,
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
    Page<UsuarioPersonaResponse> usuarioPage = usuarioService.findByUsuario(cuenta, estado, nombre,
        tipoDocumentoId, numeroDocumento,
        pageable);

    Map<String, Object> response = new HashMap<>();
    response.put("usuarios", usuarioPage.getContent());
    response.put("currentPage", usuarioPage.getNumber());
    response.put("totalItems", usuarioPage.getTotalElements());
    response.put("totalPages", usuarioPage.getTotalPages());

    return ResponseEntity.ok(response);

  }

  @GetMapping("/verificar-disponibilidad")
  public ResponseEntity<SuccessResponse<UsuarioDisponibleResponse>> verificarDisponibilidadUsuario(
      @RequestParam(value = "cuenta") String cuenta) {
    var resultado = usuarioService.verificarDisponibilidadUsuario(cuenta);
    return SuccessResponseHandler.SUCCESS(resultado);
  }

  @PostMapping
  public ResponseEntity<SuccessResponse<Boolean>> crear(
      @RequestBody CrearUsuarioRequest request) {
    boolean resultado = usuarioService.crear(request);
    return SuccessResponseHandler.SUCCESS(201, resultado);
  }

  @PutMapping("/{id}")
  public ResponseEntity<SuccessResponse<Boolean>> update(
      @PathVariable("id") UUID id,
      @RequestBody UsuarioPersonaRequest request) {
    boolean resultado = usuarioService.update(id, request);
    return SuccessResponseHandler.SUCCESS(200, resultado);
  }

}
