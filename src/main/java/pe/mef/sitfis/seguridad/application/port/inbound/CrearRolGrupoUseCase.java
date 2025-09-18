package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;

public interface CrearRolGrupoUseCase {

  RolGrupoInfoDto crear(CrearRolGrupoCommand command);
}