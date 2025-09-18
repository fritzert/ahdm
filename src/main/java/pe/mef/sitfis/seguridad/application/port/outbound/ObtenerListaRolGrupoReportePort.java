package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoReporteDto;

public interface ObtenerListaRolGrupoReportePort {

  List<ListaRolGrupoReporteDto> listarTodoReporte();
}
