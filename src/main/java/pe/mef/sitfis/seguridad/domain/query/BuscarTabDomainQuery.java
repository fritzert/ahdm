package pe.mef.sitfis.seguridad.domain.query;

import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.SubmenuId;

public record BuscarTabDomainQuery(
    String nombre,
    MenuId menuId,
    SubmenuId submenuId) {

  /**
   * Constructor compacto para preparar los criterios de búsqueda.
   */
  public BuscarTabDomainQuery {
    if (nombre != null && !nombre.trim().isEmpty()) {
      nombre = "%" + nombre.trim().toUpperCase() + "%";
    } else {
      nombre = null;
    }
  }

}