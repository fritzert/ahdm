package pe.mef.sitfis.seguridad.application.dto;

public record TabPaginadoDto(
    Long tabId,
    String tabNombre,
    Integer orden,
    String componente,
    Long menuId,
    String menuNombre,
    Long submenuId,
    String submenuNombre) {

}
