package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.RolComboDto;

public interface ListarRestriccionesPort {

  List<RolComboDto> listarRestricciones(Long id);
}
