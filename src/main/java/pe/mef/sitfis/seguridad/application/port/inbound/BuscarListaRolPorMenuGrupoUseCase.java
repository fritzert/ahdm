package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoMenuDto;

public interface BuscarListaRolPorMenuGrupoUseCase {

  List<ListaRolGrupoMenuDto> listarMenuGrupoRol(Long grupoId, Long rolId);
}
