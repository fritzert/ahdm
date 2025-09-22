package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.ActualizarRolGrupoApplicationCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;

public interface ActualizarRolGrupoUseCase {

  RolGrupoInfoDto actualizar(Long id, ActualizarRolGrupoApplicationCommand command);
}