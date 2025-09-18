package pe.mef.sitfis.seguridad.domain.aggregate.id;

/**
 * Value Object que representa el identificador unico de un Menu
 *
 */
public record MenuId(Long valor) {

  /**
   * Constructor que valida el ID del menu
   *
   * @param valor identificador numerico del menu
   * @throws IllegalArgumentException si el valor es nulo o menor o igual a cero
   */
  public MenuId {
    if (valor != null && valor <= 0) {
      throw new IllegalArgumentException("El ID del menú debe ser un número positivo");
    }
  }

  public static MenuId nuevo() {
    return new MenuId(null);
  }

  /**
   * Crea un MenuId a partir de un Long.
   *
   * @param id valor del identificador
   * @return nueva instancia de MenuId
   */
  public static MenuId de(Long id) {
    return new MenuId(id);
  }

  public boolean esNuevo() {
    return valor == null;
  }

}