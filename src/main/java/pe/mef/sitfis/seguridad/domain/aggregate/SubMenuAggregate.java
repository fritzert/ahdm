package pe.mef.sitfis.seguridad.domain.aggregate;

import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.SubmenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuNivelValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuNombreValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuOrdenValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuRutaValue;

public class SubMenuAggregate {

  private SubmenuId id;
  private final MenuId menuId;
  private final SubMenuNombreValue nombre;
  private final SubMenuNivelValue nivel;
  private final SubMenuRutaValue ruta;
  private final SubMenuOrdenValue orden;

  public SubMenuAggregate(SubmenuId id, MenuId menuId, SubMenuNombreValue nombre,
      SubMenuNivelValue nivel, SubMenuRutaValue ruta, SubMenuOrdenValue orden) {
    this.id = id;
    this.menuId = menuId;
    this.nombre = nombre;
    this.nivel = nivel;
    this.ruta = ruta;
    this.orden = orden;
    validarReglasDeNegocio();
  }

//  public static SubMenuAggregate crear(CrearMenuDomainCommand command) {
//    return new SubMenuAggregate(
//        MenuId.nuevo(),
//        command.nombre(),
//        command.orden()
//    );
//  }

  public static SubMenuAggregate reconstruir(SubmenuId id, MenuId menuId, SubMenuNombreValue nombre,
      SubMenuNivelValue nivel, SubMenuRutaValue ruta, SubMenuOrdenValue orden) {
    return new SubMenuAggregate(id, menuId, nombre, nivel, ruta, orden);
  }

  public SubMenuAggregate conId(SubmenuId nuevoId) {
    this.id = nuevoId;
    return this;
  }

  /**
   * Valida las reglas de negocio del aggregate.
   *
   * @throws IllegalArgumentException si alguna regla de negocio no se cumple
   */
  private void validarReglasDeNegocio() {
    if (nombre == null) {
      throw new IllegalArgumentException("El nombre del menu no puede ser nulo");
    }
    if (orden == null) {
      throw new IllegalArgumentException("El orden del menu no puede ser nulo");
    }
  }

  public boolean esNuevo() {
    return id == null || id.esNuevo();
  }

  public SubmenuId getId() {
    return id;
  }

  public MenuId getMenuId() {
    return menuId;
  }

  public SubMenuNombreValue getNombre() {
    return nombre;
  }

  public SubMenuNivelValue getNivel() {
    return nivel;
  }

  public SubMenuRutaValue getRuta() {
    return ruta;
  }

  public SubMenuOrdenValue getOrden() {
    return orden;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null || getClass() != obj.getClass()) {
      return false;
    }
    SubMenuAggregate that = (SubMenuAggregate) obj;
    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }
}