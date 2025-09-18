package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.command.EliminarRolGrupoCommand;

public interface EliminarRolGrupoUseCase {

  void eliminar(EliminarRolGrupoCommand command);

}
