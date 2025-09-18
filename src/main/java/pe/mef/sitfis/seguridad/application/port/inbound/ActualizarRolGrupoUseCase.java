package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.ActualizarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;

public interface ActualizarRolGrupoUseCase {

  RolGrupoInfoDto actualizar(Long id, ActualizarRolGrupoCommand command);
}