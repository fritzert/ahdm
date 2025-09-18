package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.command.ObtenerRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;

public interface ObtenerRolGrupoPort {

  //  RolGrupoAggregate obtenerPorId(Long id);
//  RolGrupoAggregate obtenerPorId(ObtenerRolGrupoCommand command); // ← ApplicationCommand

  RolGrupoInfoDto obtenerPorId(ObtenerRolGrupoCommand command); // ← ApplicationCommand

//  boolean existe(Long id);
}
