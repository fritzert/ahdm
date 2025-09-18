package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoReporteDto;

public interface ListaRolGrupoReporteUseCase {

  List<ListaRolGrupoReporteDto> listarTodoReporte();
}
