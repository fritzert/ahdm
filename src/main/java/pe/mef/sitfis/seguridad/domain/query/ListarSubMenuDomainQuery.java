package pe.mef.sitfis.seguridad.domain.query;

import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;

public record ListarSubMenuDomainQuery(String criterioNombre, MenuId menuId) {

  public ListarSubMenuDomainQuery {
    if (criterioNombre != null && !criterioNombre.trim().isEmpty()) {
      criterioNombre = "%" + criterioNombre.trim().toUpperCase() + "%";
    } else {
      criterioNombre = null;
    }

    if (menuId != null && menuId.esNuevo()) {
      menuId = null;
    }
  }

  public static ListarSubMenuDomainQuery sinFiltros() {
    return new ListarSubMenuDomainQuery(null, null);
  }

  public static ListarSubMenuDomainQuery de(String nombre, MenuId menuId) {
    return new ListarSubMenuDomainQuery(nombre, menuId);
  }
}
