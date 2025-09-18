package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.ActualizarRolCommand;
import pe.mef.sitfis.seguridad.application.dto.RolInfoDto;

public interface ActualizarRolUseCase {

  RolInfoDto actualizar(Long id, ActualizarRolCommand command);
}
