package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.dto.CrearGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoInfoDto;

public interface GuardarGrupoPort {

  GrupoInfoDto guardar(CrearGrupoDto dto);
}
