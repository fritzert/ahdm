package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.RolComboDto;

public interface ListarRolPort {

  List<RolComboDto> listar();
}
