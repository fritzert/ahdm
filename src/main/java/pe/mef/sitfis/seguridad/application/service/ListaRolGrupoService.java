package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.command.CrearListaRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.CrearListaRolGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.ListaDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoMenuDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoPaginadoDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoReporteDto;
import pe.mef.sitfis.seguridad.application.exception.BusinessException;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarListaRolGrupoPaginadoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarListaRolPorMenuGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.CrearListarRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.EliminarListaRolGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListaRolGrupoReporteUseCase;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarListaRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarListaRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaPorMenuGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaRolGrupoPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaRolGrupoReportePort;

@RequiredArgsConstructor
@Slf4j
public class ListaRolGrupoService implements
    ListaRolGrupoReporteUseCase,
    BuscarListaRolGrupoPaginadoUseCase,
    BuscarListaRolPorMenuGrupoUseCase,
    CrearListarRolGrupoUseCase,
    EliminarListaRolGrupoUseCase {

  private final ObtenerListaRolGrupoReportePort reportePort;
  private final ObtenerListaRolGrupoPaginadoPort paginadoPort;
  private final ObtenerListaPorMenuGrupoPort menuGrupoPort;
  private final GuardarListaRolGrupoPort guardarListaRolGrupoPort;
  private final EliminarListaRolGrupoPort eliminarPort;

  @Override
  public List<ListaRolGrupoReporteDto> listarTodoReporte() {
    return reportePort.listarTodoReporte();
  }

  @Override
  public Pagina<ListaRolGrupoPaginadoDto> buscarPaginado(Long grupoId, Long rolId,
      PaginaApplicationQuery query) {
    return paginadoPort.obtenerListaRolGrupoPaginado(grupoId, rolId, query);
  }

  @Override
  public List<ListaRolGrupoMenuDto> listarMenuGrupoRol(Long grupoId, Long rolId) {
    return menuGrupoPort.listarMenuGrupoRol(grupoId, rolId);
  }

  @Override
  public Boolean crear(List<CrearListaRolGrupoCommand> commandList) {
    if (commandList.isEmpty()) {
      throw new BusinessException("Error al evaluar la lista, no se encontraron datos");
    }

    var identificadores = commandList.stream().map(CrearListaRolGrupoCommand::rolGrupoId).toList();
    var idUnicos = identificadores.stream().distinct().toList();

    if (idUnicos.size() > 1) {
      throw new BusinessException("La lista debe pertenecer a un mismo rol grupo");
    }

    var dtoList = commandList.stream()
        .map(x -> new CrearListaRolGrupoDto(x.rolGrupoId(),
            new ListaDto(x.lista().menuId(), x.lista().submenuId(), x.lista().tabId())))
        .toList();

    return guardarListaRolGrupoPort.guardar(dtoList);
  }

  @Override
  public void eliminar(UUID id) {
    eliminarPort.eliminar(id);
    //deleteDataCache();
  }

}
