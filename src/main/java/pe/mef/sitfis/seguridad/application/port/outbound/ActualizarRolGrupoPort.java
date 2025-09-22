package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.domain.aggregate.RolGrupoAggregate;
import pe.mef.sitfis.seguridad.domain.command.ActualizarRolGrupoDomainCommand;

public interface ActualizarRolGrupoPort {

  RolGrupoAggregate actualizar(ActualizarRolGrupoDomainCommand command);
}
