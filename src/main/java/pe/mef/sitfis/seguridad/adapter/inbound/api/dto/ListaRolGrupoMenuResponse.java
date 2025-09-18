package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;

public record ListaRolGrupoMenuResponse(
    UUID id,
    Long rolGrupoId,
    ListaTabMenuSubmenuResponse lista) {

}
