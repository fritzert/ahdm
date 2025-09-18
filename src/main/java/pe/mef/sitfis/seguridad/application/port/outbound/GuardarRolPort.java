package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.dto.CrearRolDto;
import pe.mef.sitfis.seguridad.application.dto.RolInfoDto;

public interface GuardarRolPort {

  RolInfoDto guardar(CrearRolDto dto);
}
