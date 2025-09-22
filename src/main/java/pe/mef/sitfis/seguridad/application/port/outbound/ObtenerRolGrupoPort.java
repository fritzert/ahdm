package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.command.ObtenerRolGrupoCommand;
import pe.mef.sitfis.seguridad.domain.aggregate.RolGrupoAggregate;

public interface ObtenerRolGrupoPort {

  RolGrupoAggregate obtenerPorId(ObtenerRolGrupoCommand command);

}
