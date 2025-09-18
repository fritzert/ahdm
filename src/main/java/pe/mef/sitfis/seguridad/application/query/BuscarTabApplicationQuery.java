package pe.mef.sitfis.seguridad.application.query;

public record BuscarTabApplicationQuery(
    String nombre,
    Long menuId,
    Long submenuId) {

}