package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.command.CrearListaRolGrupoCommand;

public interface CrearListarRolGrupoUseCase {

  Boolean crear(List<CrearListaRolGrupoCommand> commandList);
}

