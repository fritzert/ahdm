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

  /**
   * Constructor alternativo que acepta valores que pueden ser nulos.
   */
  public static BuscarTabPaginadoDomainQuery crear(
      Long menuIdValor,
      Long submenuIdValor,
      Long tabIdValor,
      PaginaApplicationQuery pagina) {

    MenuId menuId = menuIdValor != null ? MenuId.de(menuIdValor) : null;
    SubmenuId submenuId = submenuIdValor != null ? SubmenuId.de(submenuIdValor) : null;
    TabId tabId = tabIdValor != null ? TabId.de(tabIdValor) : null;

    return new BuscarTabPaginadoDomainQuery(menuId, submenuId, tabId, pagina);
  }


}