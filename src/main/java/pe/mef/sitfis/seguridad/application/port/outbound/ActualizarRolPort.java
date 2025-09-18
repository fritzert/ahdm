package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.dto.ActualizarRolDto;
import pe.mef.sitfis.seguridad.application.dto.RolInfoDto;

public interface ActualizarRolPort {

  RolInfoDto actualizar(Long id, ActualizarRolDto dto);
}
