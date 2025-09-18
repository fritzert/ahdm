package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.LISTA_ROL_GRUPO_ENDPOINT;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearListaRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoMenuResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoReporteResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.spec.ListaRolGrupoApi;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper.ListaRolGrupoMapper;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarListaRolGrupoPaginadoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarListaRolPorMenuGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.CrearListarRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.EliminarListaRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListaRolGrupoReporteUseCase;

@Slf4j
@RestController
@RequestMapping(LISTA_ROL_GRUPO_ENDPOINT)
@RequiredArgsConstructor
public class ListaRolGrupoController implements ListaRolGrupoApi {

  private final ListaRolGrupoReporteUseCase reporteUseCase;
  private final BuscarListaRolGrupoPaginadoUseCase buscarListaRolGrupoPaginadoUseCase;
  private final BuscarListaRolPorMenuGrupoUseCase buscarListaRolPorMenuGrupoUseCase;
  private final CrearListarRolGrupoUseCase crearListarRolGrupoUseCase;
  private final EliminarListaRolGrupoUseCase eliminarListaRolGrupoUseCase;

  private final ListaRolGrupoMapper mapper;

  @Override
  @GetMapping("/reporte")
  public ResponseEntity<SuccessResponse<List<ListaRolGrupoReporteResponse>>> listarTodoReporte() {
    var resultado = reporteUseCase.listarTodoReporte();
    var respuesta = mapper.toListReporteResponse(resultado);
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @GetMapping("/paginado")
  public ResponseEntity<SuccessResponse<Pagina<ListaRolGrupoPaginadoResponse>>> listarRolGrupo(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "id_grupo", required = false) Long grupoId,
      @RequestParam(name = "id_rol", required = false) Long rolId) {
    var query = new PaginaApplicationQuery(page, size, null, null);
    var resultado = buscarListaRolGrupoPaginadoUseCase.buscarPaginado(grupoId, rolId, query);
    var respuesta = new Pagina<>(
        mapper.toListPaginadoResponse(resultado.contenido().stream().toList()),
        resultado.paginaActual(),
        resultado.totalPaginas(),
        resultado.totalElementos());
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @GetMapping("/menus")
  public ResponseEntity<SuccessResponse<List<ListaRolGrupoMenuResponse>>> listarMenuGrupoRol(
      @RequestParam(value = "id_grupo") Long grupoId,
      @RequestParam(name = "id_rol") Long rolId) {
    var resultado = buscarListaRolPorMenuGrupoUseCase.listarMenuGrupoRol(grupoId, rolId);
    var respuesta = mapper.toListResponse(resultado);
    return SuccessResponseHandler.SUCCESS(respuesta);
  }

  @Override
  @PostMapping
  public ResponseEntity<SuccessResponse<Boolean>> crear(
      @RequestBody @Valid List<CrearListaRolGrupoRequest> request) {
    var command = mapper.toListCommand(request);
    var respuesta = crearListarRolGrupoUseCase.crear(command);
    return SuccessResponseHandler.SUCCESS(201, respuesta);
  }

  @Override
  @DeleteMapping(value = "/{listaRolGrupoId}")
  public ResponseEntity<SuccessResponse<Void>> eliminar(
      @PathVariable("listaRolGrupoId") UUID listaRolGrupoId) {
    eliminarListaRolGrupoUseCase.eliminar(listaRolGrupoId);
    return ResponseEntity.noContent().build();
  }

}
