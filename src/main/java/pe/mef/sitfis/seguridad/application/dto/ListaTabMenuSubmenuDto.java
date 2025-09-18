package pe.mef.sitfis.seguridad.application.dto;

import java.util.UUID;

public record ListaTabMenuSubmenuDto(
    UUID listaId,
    Long menuId,
    String menuNombre,
    Long submenuId,
    String submenuNombre,
    Long tabId,
    String tabNombre) {

}
