package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.ActualizarGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.GrupoInfoDto;

public interface ActualizarGrupoUseCase {

  GrupoInfoDto actualizar(Long id, ActualizarGrupoCommand command);
}
