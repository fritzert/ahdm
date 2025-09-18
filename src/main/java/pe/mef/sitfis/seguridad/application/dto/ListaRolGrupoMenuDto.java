package pe.mef.sitfis.seguridad.application.dto;

import java.util.UUID;

public record ListaRolGrupoMenuDto(
    UUID id,
    Long rolGrupoId,
    ListaTabMenuSubmenuDto lista) {

}
