package pe.mef.sitfis.seguridad.domain.query;

import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.SubmenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.TabId;

public record BuscarTabPaginadoDomainQuery(
    MenuId menuId,
    SubmenuId submenuId,
    TabId tabId,
    PaginaApplicationQuery pagina) {

}