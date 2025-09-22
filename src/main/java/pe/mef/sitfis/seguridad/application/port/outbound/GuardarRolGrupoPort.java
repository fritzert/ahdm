package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.domain.aggregate.RolGrupoAggregate;
import pe.mef.sitfis.seguridad.domain.command.CrearRolGrupoDomainCommand;

public interface GuardarRolGrupoPort {

  RolGrupoAggregate guardar(CrearRolGrupoDomainCommand command);
}
