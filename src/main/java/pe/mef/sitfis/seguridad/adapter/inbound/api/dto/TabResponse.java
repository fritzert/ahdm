package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

public record TabResponse(
	Long menuId,
	Long SubMenuId,
	Long tabId,
    String nombre,
    String componente,
    Integer orden) {

}
