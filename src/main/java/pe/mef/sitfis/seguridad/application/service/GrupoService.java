package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.command.ActualizarGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.CrearGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.ActualizarGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.CrearGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoComboDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoInfoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoPaginadoDto;
import pe.mef.sitfis.seguridad.application.port.inbound.ActualizarGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarGrupoPaginadoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.CrearGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.EliminarGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarGrupoPaginadoReporteUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ObtenerGrupoUseCase;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarGrupoPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarGrupoPaginadoReportePort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerGrupoPort;

@RequiredArgsConstructor
@Slf4j
public class GrupoService implements
    ListarGrupoUseCase,
    ListarGrupoPaginadoReporteUseCase,
    BuscarGrupoPaginadoUseCase,
    ObtenerGrupoUseCase,
    CrearGrupoUseCase,
    ActualizarGrupoUseCase,
    EliminarGrupoUseCase {

  private final ListarGrupoPort listarGrupoPort;
  private final ListarGrupoPaginadoReportePort paginadoPort;
  private final BuscarGrupoPaginadoPort buscarGrupoPaginadoPort;
  private final ObtenerGrupoPort obtenerGrupoPort;
  private final GuardarGrupoPort guardarGrupoPort;
  private final ActualizarGrupoPort actualizarGrupoPort;
  private final EliminarGrupoPort eliminarGrupoPort;

  @Override
  public List<GrupoComboDto> listarCombo() {
    return listarGrupoPort.listar();
  }

  @Override
  public List<GrupoPaginadoDto> listarTodoReporte() {
    return paginadoPort.listarTodoReporte();
  }

  @Override
  public Pagina<GrupoPaginadoDto> buscarGrupoPaginado(String nombre, Long grupoId, Long rolId,
      PaginaApplicationQuery query) {
    return buscarGrupoPaginadoPort.buscarGrupoPaginado(nombre, grupoId, rolId, query);
  }

  @Override
  public GrupoDto obtenerPorId(Long grupoId) {
    return obtenerGrupoPort.obtenerPorId(grupoId);
  }

  @Override
  public GrupoInfoDto crear(CrearGrupoCommand command) {
    var dto = new CrearGrupoDto(
        command.nombre(),
        command.codigo()
    );
    return guardarGrupoPort.guardar(dto);
  }

  @Override
  public GrupoInfoDto actualizar(Long id, ActualizarGrupoCommand command) {
    var dto = new ActualizarGrupoDto(
        command.nombre(),
        command.codigo()
    );
    return actualizarGrupoPort.actualizar(id, dto);
  }

  @Override
  public void eliminar(Long id) {
    eliminarGrupoPort.eliminar(id);
    //deleteDataCache();
  }

}
