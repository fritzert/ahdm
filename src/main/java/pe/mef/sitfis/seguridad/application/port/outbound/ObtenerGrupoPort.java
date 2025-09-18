package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.dto.GrupoDto;

public interface ObtenerGrupoPort {

  GrupoDto obtenerPorId(Long id);
}
