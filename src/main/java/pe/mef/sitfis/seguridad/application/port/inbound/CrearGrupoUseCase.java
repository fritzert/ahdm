package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.CrearGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.GrupoInfoDto;

public interface CrearGrupoUseCase {

  GrupoInfoDto crear(CrearGrupoCommand command);
}
