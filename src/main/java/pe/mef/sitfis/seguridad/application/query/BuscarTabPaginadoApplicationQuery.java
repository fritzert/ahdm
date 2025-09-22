package pe.mef.sitfis.seguridad.application.query;

public record BuscarTabPaginadoApplicationQuery(
    Long menuId,
    Long submenuId,
    Long tabId,
    PaginaApplicationQuery paginaApplicationQuery) {

}
