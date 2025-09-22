package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.ROL_GRUPO_ENDPOINT;

import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolGrupoInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolGrupoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.RolGrupoApiMapper;
import pe.mef.sitfis.seguridad.adapter.inbound.api.spec.RolGrupoApi;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.application.port.inbound.ActualizarRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.CrearRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.EliminarRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRolGrupoPorGrupoIdUseCase;

@Slf4j
@RestController
@RequestMapping(ROL_GRUPO_ENDPOINT)
@RequiredArgsConstructor
public class RolGrupoController implements RolGrupoApi {

  private final ListarRolGrupoPorGrupoIdUseCase rolGrupoPorGrupoIdUseCase;
  private final CrearRolGrupoUseCase crearRolGrupoUseCase;
  private final ActualizarRolGrupoUseCase actualizarRolGrupoUseCase;
  private final EliminarRolGrupoUseCase eliminarRolGrupoUseCase;
  private final RolGrupoApiMapper mapper;

  @Override
  @GetMapping("/grupo/{grupoId}")
  public ResponseEntity<SuccessResponse<List<RolGrupoResponse>>> listarPorGrupoId(
      @PathVariable("grupoId") Long grupoId) {
    var query = mapper.toQuery(grupoId);
    var resultado = rolGrupoPorGrupoIdUseCase.listarPorGrupoId(query);
    var response = mapper.toListResponse(resultado);
    return SuccessResponseHandler.SUCCESS(response);
  }

//  @Override
//  @PostMapping("/crear-o-actualizar")
//  public ResponseEntity<SuccessResponse<String>> crearOActualizar(
//      @RequestBody @Valid List<CrearActualizarRolGrupoRequest> requestList) {
//    var command = requestList.stream().map(mapper::toCommand).toList();
//    crearOActualizarUseCase.crearOActualizar(command);
//    return SuccessResponseHandler.SUCCESS("Rol/Grupo creado o actualizado correctamente.");
//  }

  @Override
  @PostMapping
  public ResponseEntity<SuccessResponse<RolGrupoInfoResponse>> crear(
      @RequestBody @Valid CrearRolGrupoRequest request) {
    var command = mapper.toCommand(request);
    var resultado = crearRolGrupoUseCase.crear(command);
    var respuesta = mapper.toInfoResponse(resultado);
    return SuccessResponseHandler.SUCCESS(201, respuesta);
  }

  @Override
  @PutMapping("/{rolGrupoId}")
  public ResponseEntity<SuccessResponse<RolGrupoInfoResponse>> actualizar(
      @PathVariable("rolGrupoId") Long rolGrupoId,
      @RequestBody @Valid ActualizarRolGrupoRequest request) {
    var command = mapper.toCommand(request);
    var resultado = actualizarRolGrupoUseCase.actualizar(rolGrupoId, command);
    var respuesta = mapper.toInfoResponse(resultado);
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @DeleteMapping(value = "/{grupoId}")
  public ResponseEntity<SuccessResponse<Void>> eliminar(@PathVariable("grupoId") Long grupoId) {
    var command = mapper.toCommand(grupoId);
    eliminarRolGrupoUseCase.eliminar(command);
    return ResponseEntity.noContent().build();
  }

}
