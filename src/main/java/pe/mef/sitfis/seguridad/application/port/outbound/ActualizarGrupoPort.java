package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.dto.ActualizarGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoInfoDto;

public interface ActualizarGrupoPort {

  GrupoInfoDto actualizar(Long id, ActualizarGrupoDto dto);
}
