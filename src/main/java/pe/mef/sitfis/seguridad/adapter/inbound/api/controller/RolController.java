package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.ROLES_ENDPOINT;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.spec.RolApi;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper.RolMapper;
import pe.mef.sitfis.seguridad.application.port.inbound.ActualizarRolUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarRolPaginadoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.CrearRolUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.EliminarRolUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRestriccionesUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRolPaginadoReporteUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRolUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ObtenerRolUseCase;

@Slf4j
@RestController
@RequestMapping(ROLES_ENDPOINT)
@RequiredArgsConstructor
public class RolController implements RolApi {

  private final ListarRolUseCase listarRolUseCase;
  private final ListarRolPaginadoReporteUseCase paginadoUseCase;
  private final BuscarRolPaginadoUseCase buscarRolPaginado;
  private final ObtenerRolUseCase obtenerRolUseCase;
  private final CrearRolUseCase crearRolUseCase;
  private final ActualizarRolUseCase actualizarRolUseCase;
  private final EliminarRolUseCase eliminarRolUseCase;
  private final ListarRestriccionesUseCase listarRestriccionesUseCase;

  private final RolMapper mapper;

  @Override
  @GetMapping
  public ResponseEntity<SuccessResponse<List<RolComboResponse>>> listar() {
    var resultado = listarRolUseCase.listarCombo();
    var respuesta = resultado.stream()
        .map(mapper::toComboResponse)
        .toList();
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @GetMapping("/reporte")
  public ResponseEntity<SuccessResponse<List<RolPaginadoResponse>>> listarTodoReporte() {
    var resultado = paginadoUseCase.listarTodoReporte();
    var respuesta = resultado.stream()
        .map(mapper::toPaginadoResponse)
        .toList();
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @GetMapping("/paginado")
  public ResponseEntity<SuccessResponse<Pagina<RolPaginadoResponse>>> buscarRolPaginado(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "nombre", required = false) String nombre) {
    var query = new PaginaApplicationQuery(page, size, null, null);
    var resultado = buscarRolPaginado.buscarRolPaginado(nombre, query);
    var respuesta = new Pagina<>(
        mapper.toListPaginadoResponse(resultado.contenido().stream().toList()),
        resultado.paginaActual(),
        resultado.totalPaginas(),
        resultado.totalElementos());
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @GetMapping("/{rolId}")
  public ResponseEntity<SuccessResponse<RolResponse>> obtenerPorId(@PathVariable("rolId") Long rolId) {
    var resultado = obtenerRolUseCase.obtenerPorId(rolId);
    var respuesta = mapper.toResponse(resultado);
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @PostMapping
  public ResponseEntity<SuccessResponse<RolInfoResponse>> crear(
      @RequestBody @Valid CrearRolRequest request) {
    var command = mapper.toCommand(request);
    var resultado = crearRolUseCase.crear(command);
    var respuesta = mapper.toInfoResponse(resultado);
    return SuccessResponseHandler.SUCCESS(201, respuesta);
  }

  @Override
  @PutMapping("/{rolId}")
  public ResponseEntity<SuccessResponse<RolInfoResponse>> actualizar(
      @PathVariable("rolId") Long rolId,
      @RequestBody @Valid ActualizarRolRequest request) {
    var command = mapper.toActualizarCommand(request);
    var resultado = actualizarRolUseCase.actualizar(rolId, command);
    var respuesta = mapper.toInfoResponse(resultado);
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @DeleteMapping(value = "/{rolId}")
  public ResponseEntity<SuccessResponse<Void>> eliminar(@PathVariable("rolId") Long rolId) {
    eliminarRolUseCase.eliminar(rolId);
    return ResponseEntity.noContent().build();
  }

  @Override
  @GetMapping("/restriccion/{grupoId}")
  public ResponseEntity<SuccessResponse<List<RolComboResponse>>> listarRestricciones(
      @PathVariable("grupoId") Long grupoId) {
    var resultado = listarRestriccionesUseCase.listarComboRestricciones(grupoId);
    var respuesta = resultado.stream()
        .map(mapper::toComboResponse)
        .toList();
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

}
