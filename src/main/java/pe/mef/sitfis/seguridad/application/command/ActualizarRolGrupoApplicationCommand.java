package pe.mef.sitfis.seguridad.application.command;

public record ActualizarRolGrupoApplicationCommand(
    Long id,
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