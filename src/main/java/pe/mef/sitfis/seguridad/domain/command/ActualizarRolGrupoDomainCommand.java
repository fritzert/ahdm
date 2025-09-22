package pe.mef.sitfis.seguridad.domain.command;

import pe.mef.sitfis.seguridad.domain.aggregate.id.GrupoId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolGrupoId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAdjuntarArchivoValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAsignarRecursosValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagConsultaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarBandejaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarEtapaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagOperacionValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagRestriccionValue;

public record ActualizarRolGrupoDomainCommand(
    RolGrupoId id,
    RolId rolId,
    GrupoId grupoId,
    RolGrupoFlagRestriccionValue flagRestriccion,
    RolGrupoFlagConsultaValue flagConsulta,
    RolGrupoFlagOperacionValue flagOperacion,
    RolGrupoFlagAsignarRecursosValue flagAsignarRecursos,
    RolGrupoFlagEnviarBandejaValue flagEnviarBandeja,
    RolGrupoFlagEnviarEtapaValue flagEnviarEtapa,
    RolGrupoFlagAdjuntarArchivoValue flagAdjuntarArchivo) {

}
