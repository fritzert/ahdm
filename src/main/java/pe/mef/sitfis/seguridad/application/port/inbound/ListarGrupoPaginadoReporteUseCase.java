package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.GrupoPaginadoDto;

public interface ListarGrupoPaginadoReporteUseCase {

  List<GrupoPaginadoDto> listarTodoReporte();
}
