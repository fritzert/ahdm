package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.GrupoComboDto;

public interface ListarGrupoPort {

  List<GrupoComboDto> listar();
}
