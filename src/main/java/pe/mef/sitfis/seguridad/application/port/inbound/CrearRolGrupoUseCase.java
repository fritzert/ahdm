package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoApplicationCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;

public interface CrearRolGrupoUseCase {

  RolGrupoInfoDto crear(CrearRolGrupoApplicationCommand command);
}