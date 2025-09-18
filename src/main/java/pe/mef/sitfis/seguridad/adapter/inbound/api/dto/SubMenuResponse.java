package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

public record SubMenuResponse(
    Long menuId,
    Long subMenuId,
    String nombre,
    String ruta,
    Integer nivel,
    Integer orden) {

}