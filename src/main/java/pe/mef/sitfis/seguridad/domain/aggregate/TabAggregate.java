package pe.mef.sitfis.seguridad.domain.aggregate;

import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.SubmenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.TabId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabComponenteValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabNombreValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabOrdenValue;

public class TabAggregate {

  private TabId id;
  private final TabNombreValue nombre;
  private final TabComponenteValue componente;
  private final TabOrdenValue orden;
  private final MenuId menuId;
  private final SubmenuId submenuId;

  /**
   * Constructor para crear un Tab Aggregate.
   *
   * @param id         identificador único del tab
   * @param nombre     valor que representa el nombre del tab
   * @param componente valor que representa el componente del tab
   * @param orden      valor que representa el orden del tab
   * @param menuId     identificador del menú al que pertenece
   * @param submenuId  identificador del submenú al que pertenece
   */
  public TabAggregate(TabId id, TabNombreValue nombre, TabComponenteValue componente,
      TabOrdenValue orden, MenuId menuId, SubmenuId submenuId) {
    this.id = id;
    this.nombre = nombre;
    this.componente = componente;
    this.orden = orden;
    this.menuId = menuId;
    this.submenuId = submenuId;
    validarInvariantes();
  }

  public static TabAggregate reconstruir(TabId id, TabNombreValue nombre,
      TabComponenteValue componente,
      TabOrdenValue orden, MenuId menuId, SubmenuId submenuId) {
    return new TabAggregate(id, nombre, componente, orden, menuId, submenuId);
  }

  public TabAggregate conId(TabId nuevoId) {
    this.id = nuevoId;
    return this;
  }

  /**
   * Valida las reglas de negocio del aggregate.
   *
   * @throws IllegalArgumentException si alguna regla de negocio no se cumple
   */
  private void validarInvariantes() {
    if (nombre == null) {
      throw new IllegalArgumentException("El nombre del tab no puede ser nulo");
    }
    if (componente == null) {
      throw new IllegalArgumentException("El componente del tab no puede ser nulo");
    }
    if (orden == null) {
      throw new IllegalArgumentException("El orden del tab no puede ser nulo");
    }
    if (menuId == null) {
      throw new IllegalArgumentException("El ID del menú no puede ser nulo");
    }
    if (submenuId == null) {
      throw new IllegalArgumentException("El ID del submenú no puede ser nulo");
    }
  }

  /**
   * Verifica si el tab coincide con el criterio de búsqueda por nombre.
   *
   * @param criterio cadena de texto para filtrar por nombre
   * @return verdadero si el tab coincide con el criterio
   */
  public boolean coincideConNombre(String criterio) {
    if (criterio == null || criterio.trim().isEmpty()) {
      return true;
    }
    return nombre.contiene(criterio);
  }

  /**
   * Verifica si el tab pertenece al menú especificado.
   *
   * @param menuIdBusqueda ID del menú a verificar
   * @return verdadero si el tab pertenece al menú
   */
  public boolean perteneceAlMenu(MenuId menuIdBusqueda) {
    return menuId.equals(menuIdBusqueda);
  }

  /**
   * Verifica si el tab pertenece al submenú especificado.
   *
   * @param submenuIdBusqueda ID del submenú a verificar
   * @return verdadero si el tab pertenece al submenú
   */
  public boolean perteneceAlSubmenu(SubmenuId submenuIdBusqueda) {
    return submenuId.equals(submenuIdBusqueda);
  }

  public boolean esNuevo() {
    return id == null || id.esNuevo();
  }

  public TabId getId() {
    return id;
  }

  public TabNombreValue getNombre() {
    return nombre;
  }

  public TabComponenteValue getComponente() {
    return componente;
  }

  public TabOrdenValue getOrden() {
    return orden;
  }

  public MenuId getMenuId() {
    return menuId;
  }

  public SubmenuId getSubmenuId() {
    return submenuId;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    TabAggregate that = (TabAggregate) obj;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}
