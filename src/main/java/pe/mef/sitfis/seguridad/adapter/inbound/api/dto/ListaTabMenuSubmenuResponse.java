package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;

public record ListaTabMenuSubmenuResponse(
    UUID listaId,
    Long menuId,
    String menuNombre,
    Long submenuId,
    String submenuNombre,
    Long tabId,
    String tabNombre) {

}
