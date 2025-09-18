package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

public record MenuResponse(
    Long menuId,
    String nombre,
    String ruta,
    Integer orden) {

}