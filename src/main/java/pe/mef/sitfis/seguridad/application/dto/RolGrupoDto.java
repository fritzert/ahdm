package pe.mef.sitfis.seguridad.application.dto;

import java.time.LocalDateTime;

public record RolGrupoDto(
    Long id,
    Long rolId,
    String rolNombre,
    Long grupoId,
    String flagRestriccion,
    String flagConsulta,
    String flagOperacion,
    String flagAsignarRecursos,
    String flagEnviarBandeja,
    String flagEnviarEtapa,
    String flagAdjuntarArchivo,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
