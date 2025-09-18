package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.dto.RolDto;

public interface ObtenerRolUseCase {

  RolDto obtenerPorId(Long id);
}
