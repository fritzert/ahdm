package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.RolPaginadoDto;

public interface ListarRolPaginadoReportePort {

  List<RolPaginadoDto> listarTodoReporte();
}
