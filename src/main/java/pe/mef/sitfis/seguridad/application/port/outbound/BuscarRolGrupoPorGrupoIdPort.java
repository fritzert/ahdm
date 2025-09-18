package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.application.query.ListarRolGrupoApplicationQuery;

public interface BuscarRolGrupoPorGrupoIdPort {

  //  List<RolGrupoDto> buscarPorGrupoId(Long id);
  List<RolGrupoDto> buscarPorGrupoId(ListarRolGrupoApplicationQuery query);
}
