package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

public record TabPaginadoResponse(
    Long tabId,
    String tabNombre,
    Integer orden,
    String componente,
    Long menuId,
    String menuNombre,
    Long submenuId,
    String submenuNombre) {

}
