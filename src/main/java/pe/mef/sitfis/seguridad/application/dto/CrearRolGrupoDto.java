package pe.mef.sitfis.seguridad.application.dto;

public record CrearRolGrupoDto(
    Long rolId,
    Long grupoId,
    int flagRestriccion,
    int flagConsulta,
    int flagOperacion,
    int flagAsignarRecursos,
    int flagEnviarBandeja,
    int flagEnviarEtapa,
    int flagAdjuntarArchivo) {

}
