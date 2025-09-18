package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListaRolGrupoReporteResponse(
    UUID id,
    Long rolGrupoId,
    Long rolId,
    String rolNombre,
    Long grupoId,
    String grupoNombre,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
