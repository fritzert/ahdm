package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;

public record ListaRolGrupoPaginadoResponse(
    UUID id,
    Long rolGrupoId,
    Long rolId,
    String rolNombre,
    Long grupoId,
    String grupoNombre) {

}
