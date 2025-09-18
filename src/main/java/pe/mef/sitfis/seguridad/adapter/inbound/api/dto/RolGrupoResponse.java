package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.time.LocalDateTime;

public record RolGrupoResponse(
    Long id,
    Long rolId,
    String rolNombre,
    Long grupoId,
    int flagRestriccion,
    int flagConsulta,
    int flagOperacion,
    int flagAsignarRecursos,
    int flagEnviarBandeja,
    int flagEnviarEtapa,
    int flagAdjuntarArchivo,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
