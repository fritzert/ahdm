package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.command.ActualizarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.ActualizarRolGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;

public interface ActualizarRolGrupoPort {

  //  int actualizar(CrearRolGrupoCommand grupoCommand);
//  RolGrupoInfoDto actualizar(Long id, ActualizarRolGrupoDto dto);
  RolGrupoInfoDto actualizar(ActualizarRolGrupoCommand command);
}
