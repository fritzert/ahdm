package pe.mef.sitfis.seguridad.application.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public record ListaRolGrupoReporteDto(
    UUID id,
    Long rolGrupoId,
    Long rolId,
    String rolNombre,
    Long grupoId,
    String grupoNombre,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
