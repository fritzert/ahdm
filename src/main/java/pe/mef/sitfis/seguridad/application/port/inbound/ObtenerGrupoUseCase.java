package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.dto.GrupoDto;

public interface ObtenerGrupoUseCase {

  GrupoDto obtenerPorId(Long grupoId);
}
