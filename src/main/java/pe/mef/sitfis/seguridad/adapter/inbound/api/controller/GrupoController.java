package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.GRUPOS_ENDPOINT;

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
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.spec.GrupoApi;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper.GrupoMapper;
import pe.mef.sitfis.seguridad.application.port.inbound.ActualizarGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarGrupoPaginadoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.CrearGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.EliminarGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarGrupoPaginadoReporteUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ObtenerGrupoUseCase;

@Slf4j
@RestController
@RequestMapping(GRUPOS_ENDPOINT)
@RequiredArgsConstructor
public class GrupoController implements GrupoApi {

  private final ListarGrupoUseCase listarGrupoUseCase;
  private final ListarGrupoPaginadoReporteUseCase reporteUseCase;
  private final BuscarGrupoPaginadoUseCase buscarGrupoPaginado;
  private final ObtenerGrupoUseCase obtenerGrupoUseCase;
  private final CrearGrupoUseCase crearGrupoUseCase;
  private final ActualizarGrupoUseCase actualizarGrupoUseCase;
  private final EliminarGrupoUseCase eliminarGrupoUseCase;

  private final GrupoMapper mapper;

  @Override
  @GetMapping
  public ResponseEntity<SuccessResponse<List<GrupoComboResponse>>> listar() {
    var resultado = listarGrupoUseCase.listarCombo();
    var respuesta = resultado.stream()
        .map(mapper::toComboResponse)
        .toList();
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @GetMapping("/reporte")
  public ResponseEntity<SuccessResponse<List<GrupoPaginadoResponse>>> listarTodoReporte() {
    var resultado = reporteUseCase.listarTodoReporte();
    var respuesta = mapper.toListPaginadoResponse(resultado);
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @GetMapping("/paginado")
  public ResponseEntity<SuccessResponse<Pagina<GrupoPaginadoResponse>>> buscarGrupoPaginado(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "nombre", required = false) String nombre,
      @RequestParam(value = "id_grupo", required = false) Long grupoId,
      @RequestParam(value = "id_rol", required = false) Long rolId) {
    var query = new PaginaApplicationQuery(page, size, null, null);
    var resultado = buscarGrupoPaginado.buscarGrupoPaginado(nombre, grupoId, rolId, query);
    var respuesta = new Pagina<>(
        mapper.toListPaginadoResponse(resultado.contenido().stream().toList()),
        resultado.paginaActual(),
        resultado.totalPaginas(),
        resultado.totalElementos());
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @GetMapping("/{grupoId}")
  public ResponseEntity<SuccessResponse<GrupoResponse>> obtenerPorId(
      @PathVariable("grupoId") Long grupoId) {
    var resultado = obtenerGrupoUseCase.obtenerPorId(grupoId);
    var respuesta = mapper.toResponse(resultado);
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @PostMapping
  public ResponseEntity<SuccessResponse<GrupoInfoResponse>> crear(
      @RequestBody @Valid CrearGrupoRequest request) {
    var command = mapper.toCommand(request);
    var resultado = crearGrupoUseCase.crear(command);
    var respuesta = mapper.toInfoResponse(resultado);
    return SuccessResponseHandler.SUCCESS(201, respuesta);
  }

  @Override
  @PutMapping("/{grupoId}")
  public ResponseEntity<SuccessResponse<GrupoInfoResponse>> actualizar(
      @PathVariable("grupoId") Long grupoId,
      @RequestBody @Valid ActualizarGrupoRequest request) {
    var command = mapper.toActualizarCommand(request);
    var resultado = actualizarGrupoUseCase.actualizar(grupoId, command);
    var respuesta = mapper.toInfoResponse(resultado);
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @DeleteMapping(value = "/{grupoId}")
  public ResponseEntity<SuccessResponse<Void>> eliminar(@PathVariable("grupoId") Long grupoId) {
    eliminarGrupoUseCase.eliminar(grupoId);
    return ResponseEntity.noContent().build();
  }

}
