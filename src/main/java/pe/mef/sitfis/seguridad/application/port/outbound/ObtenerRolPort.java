package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.dto.RolDto;

public interface ObtenerRolPort {

  RolDto obtenerPorId(Long id);
}
