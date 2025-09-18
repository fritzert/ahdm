package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;

public interface GuardarRolGrupoPort {

  //  RolGrupoInfoDto guardar(CrearRolGrupoDto dto);
//  RolGrupoAggregate guardar(GuardarRolGrupoCommand command);

  //  RolGrupoInfoDto guardar(GuardarRolGrupoCommand command);
  RolGrupoInfoDto guardar(CrearRolGrupoCommand command);
}
