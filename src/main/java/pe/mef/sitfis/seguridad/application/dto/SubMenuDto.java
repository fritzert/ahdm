package pe.mef.sitfis.seguridad.application.dto;

public record SubMenuDto(
    Long menuId,
    Long subMenuId,
    String nombre,
    String ruta,
    Integer nivel,
    Integer orden) {

}
