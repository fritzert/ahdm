package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.CrearRolCommand;
import pe.mef.sitfis.seguridad.application.dto.RolInfoDto;

public interface CrearRolUseCase {

  RolInfoDto crear(CrearRolCommand command);
}
