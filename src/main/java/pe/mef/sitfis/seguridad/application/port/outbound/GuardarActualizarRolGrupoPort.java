package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoCommand;

public interface GuardarActualizarRolGrupoPort {

  int guardarActualizar(List<CrearRolGrupoCommand> grupoCommandList);

}
