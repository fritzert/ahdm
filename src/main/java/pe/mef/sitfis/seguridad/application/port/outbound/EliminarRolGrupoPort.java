package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.command.EliminarRolGrupoCommand;

public interface EliminarRolGrupoPort {

  //  void eliminar(Long id);
  void eliminar(EliminarRolGrupoCommand command);
}
