package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.RolPaginadoDto;

public interface ListarRolPaginadoReporteUseCase {

  List<RolPaginadoDto> listarTodoReporte();
}
