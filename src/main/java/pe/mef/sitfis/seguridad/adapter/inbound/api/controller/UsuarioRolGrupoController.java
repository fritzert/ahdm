package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.USUARIOS_ROLES_GRUPOS;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.EliminarMultipleUsuarioRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioCopiarRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoDTO;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoPrincipalRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoRequest;
import pe.mef.sitfis.seguridad.application.exception.ResourceNotFoundException;
import pe.mef.sitfis.seguridad.application.exception.ServiceException;
import pe.mef.sitfis.seguridad.application.service.UsuarioRolGrupoService;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;

@Slf4j
@RestController
@RequestMapping(USUARIOS_ROLES_GRUPOS)
@RequiredArgsConstructor
public class UsuarioRolGrupoController {

  private final UsuarioRolGrupoService usuarioRolGrupoService;

  @GetMapping("/paginado")
  public ResponseEntity<SuccessResponse<Pagina<UsuarioRolGrupoPaginadoResponse>>> buscarUsuarioDocumentoPaginado(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "cuenta", required = false) String cuenta,
      @RequestParam(value = "tipoDocumentoId", defaultValue = "-1") Long tipoDocumentoId,
      @RequestParam(value = "numeroDocumento", required = false) String numeroDocumento) {
    var query = new PaginaApplicationQuery(page, size, "id", "DESC");
    Pagina<UsuarioRolGrupoPaginadoResponse> resultado = usuarioRolGrupoService.buscarUsuarioRolGrupoPaginado(
        cuenta, tipoDocumentoId, numeroDocumento, query);

    var paginado = new Pagina<>(
//                resultado.contenido().stream().map(mapper::toResponse).toList(),
        resultado.contenido(),
        resultado.paginaActual(),
        resultado.totalPaginas(),
        resultado.totalElementos());
    return SuccessResponseHandler.SUCCESS(paginado);
  }

  @GetMapping("/{id}")
  public ResponseEntity<?> listarTodos(@PathVariable("id") UUID id) {
    try {
      Map<String, String> resp = new HashMap<>();
      if (id == null) {
        resp.put("response", String.format("El id=%s especificado no es válido", id));
        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.toString());
      }

      List<UsuarioRolGrupoDTO> usuarioRolGrupoDTO = usuarioRolGrupoService.findByIDUsuario(id);

      return ResponseEntity.ok(usuarioRolGrupoDTO);

    } catch (ServiceException e) {
      e.printStackTrace();
      return ResponseEntity.internalServerError().build();
    }
  }

  @Operation(summary = "Registra el usuario copiando accesos de otro usuario", description = "returna 200 si el usuario no existe.", tags = {
      "SITFIS"})
  @PostMapping("/copiar-roles")
  public ResponseEntity<SuccessResponse<String>> grabarCopiarRoles(
      @RequestBody UsuarioCopiarRolRequest request) {
//        try {
    boolean resultado = usuarioRolGrupoService.grabarCopiarRoles(request);
    if (resultado == false) {
      throw new ResourceNotFoundException(HttpStatus.BAD_REQUEST.toString());
    }

    return SuccessResponseHandler.SUCCESS(201, "registro exitoso");
  }

  @PostMapping("/crear-o-actualizar")
  public ResponseEntity<SuccessResponse<Boolean>> crearActualizarUsuarioRolGrupo(
      @RequestBody @Valid List<UsuarioRolGrupoRequest> request) {
    var resultado = usuarioRolGrupoService.crearActualizarUsuarioRolGrupo(request);
    return SuccessResponseHandler.SUCCESS(201, resultado);
  }

  @PutMapping("/usuario-grupo-grincipal/{usuarioId}")
  public ResponseEntity<SuccessResponse<Boolean>> actualizarUsuarioRolGrupoPrincipal(
      @PathVariable("usuarioId") UUID usuarioId,
      @RequestBody @Valid UsuarioRolGrupoPrincipalRequest request) {
    var resultado = usuarioRolGrupoService.actualizarUsuarioRolGrupoPrincipal(usuarioId, request);
    return SuccessResponseHandler.SUCCESS(200, resultado);
  }

  @PostMapping("/eliminar-multiple-usuario-rol-grupo")
  public ResponseEntity<SuccessResponse<Void>> eliminarMultipleUsuarioRolGrupo(
      @RequestBody EliminarMultipleUsuarioRolGrupoRequest request) {
    usuarioRolGrupoService.eliminarMultipleUsuarioRolGrupo(request);
    return SuccessResponseHandler.SUCCESS(204, "Se realizó la eliminación satisfactoriamente.");
  }

}

