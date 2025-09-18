package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.application.query.ListarRolGrupoApplicationQuery;

public interface ListarRolGrupoPorGrupoIdUseCase {

  List<RolGrupoDto> listarPorGrupoId(ListarRolGrupoApplicationQuery query);

}
