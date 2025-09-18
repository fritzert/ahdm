package pe.mef.sitfis.seguridad.application.dto;

import java.util.UUID;

public record ListaRolGrupoPaginadoDto(
    UUID id,
    Long rolGrupoId,
    Long rolId,
    String rolNombre,
    Long grupoId,
    String grupoNombre) {

}