package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoMenuDto;

public interface ObtenerListaPorMenuGrupoPort {

  List<ListaRolGrupoMenuDto> listarMenuGrupoRol(Long grupoId, Long rolId);

}
