package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.domain.query.ListarRolGrupoDomainQuery;

public interface BuscarRolGrupoPorGrupoIdPort {

  List<RolGrupoDto> buscarPorGrupoId(ListarRolGrupoDomainQuery query);
}
