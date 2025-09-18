package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.CrearListaRolGrupoDto;

public interface GuardarListaRolGrupoPort {

  Boolean guardar(List<CrearListaRolGrupoDto> dtoList);
}
