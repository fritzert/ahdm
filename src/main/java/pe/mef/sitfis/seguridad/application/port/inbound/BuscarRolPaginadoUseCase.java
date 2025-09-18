package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.dto.RolPaginadoDto;

public interface BuscarRolPaginadoUseCase {

  Pagina<RolPaginadoDto> buscarRolPaginado(String nombre, PaginaApplicationQuery query);

}
