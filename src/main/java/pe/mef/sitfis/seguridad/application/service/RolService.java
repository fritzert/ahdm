package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.command.ActualizarRolCommand;
import pe.mef.sitfis.seguridad.application.command.CrearRolCommand;
import pe.mef.sitfis.seguridad.application.dto.ActualizarRolDto;
import pe.mef.sitfis.seguridad.application.dto.CrearRolDto;
import pe.mef.sitfis.seguridad.application.dto.RolComboDto;
import pe.mef.sitfis.seguridad.application.dto.RolDto;
import pe.mef.sitfis.seguridad.application.dto.RolInfoDto;
import pe.mef.sitfis.seguridad.application.dto.RolPaginadoDto;
import pe.mef.sitfis.seguridad.application.port.inbound.ActualizarRolUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarRolPaginadoUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.CrearRolUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.EliminarRolUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRestriccionesPort;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRestriccionesUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRolPaginadoReporteUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRolUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.ObtenerRolUseCase;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarRolPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarRolPaginadoReportePort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerRolPort;

@Service
@RequiredArgsConstructor
public class RolService implements
    ListarRolUseCase,
    ListarRolPaginadoReporteUseCase,
    BuscarRolPaginadoUseCase,
    ObtenerRolUseCase,
    CrearRolUseCase,
    ActualizarRolUseCase,
    EliminarRolUseCase,
    ListarRestriccionesUseCase {

  private final ListarRolPort listarRolPort;
  private final ListarRolPaginadoReportePort paginadoPort;
  private final BuscarRolPaginadoPort buscarRolPaginadoPort;
  private final ObtenerRolPort obtenerRolPort;
  private final GuardarRolPort guardarRolPort;
  private final ActualizarRolPort actualizarRolPort;
  private final EliminarRolPort eliminarRolPort;
  private final ListarRestriccionesPort listarRestriccionesPort;

  @Override
  public List<RolComboDto> listarCombo() {
    return listarRolPort.listar();
  }

  @Override
  public List<RolPaginadoDto> listarTodoReporte() {
    return paginadoPort.listarTodoReporte();
  }

  @Override
  public Pagina<RolPaginadoDto> buscarRolPaginado(String nombre, PaginaApplicationQuery query) {
    return buscarRolPaginadoPort.buscarRolPaginado(nombre, query);
  }

  @Override
  public RolDto obtenerPorId(Long id) {
    return obtenerRolPort.obtenerPorId(id);
  }

  @Override
  public RolInfoDto crear(CrearRolCommand command) {
    var dto = new CrearRolDto(
        command.nombre()
    );
    return guardarRolPort.guardar(dto);
  }

  @Override
  public RolInfoDto actualizar(Long id, ActualizarRolCommand command) {
    var dto = new ActualizarRolDto(
        command.nombre()
    );
    return actualizarRolPort.actualizar(id, dto);
  }

  @Override
  public void eliminar(Long id) {
    eliminarRolPort.eliminar(id);
  }

  @Override
  public List<RolComboDto> listarComboRestricciones(Long id) {
    return listarRestriccionesPort.listarRestricciones(id);
  }

}