package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.GrupoPaginadoDto;

public interface ListarGrupoPaginadoReportePort {

  List<GrupoPaginadoDto> listarTodoReporte();
}
