package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoCommand;

public interface CrearOActualizarRolGrupoUseCase {

  void crearOActualizar(List<CrearRolGrupoCommand> request);

}
