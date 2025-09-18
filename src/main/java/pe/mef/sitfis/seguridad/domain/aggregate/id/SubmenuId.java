package pe.mef.sitfis.seguridad.domain.aggregate.id;

/**
 * Value Object que representa el identificador único de un Submenu
 */
public record SubmenuId(Long valor) {

  /**
   * Constructor que valida el ID del submenu
   *
   * @param valor identificador numérico del submenu
   * @throws IllegalArgumentException si el valor es nulo o menor o igual a cero
   */
  public SubmenuId {
    if (valor == null || valor <= 0) {
      throw new IllegalArgumentException("El ID del submenú debe ser un número positivo");
    }
  }

  public static SubmenuId nuevo() {
    return new SubmenuId(null);
  }

  /**
   * Crea un SubmenuId a partir de un Long.
   *
   * @param id valor del identificador
   * @return nueva instancia de SubmenuId
   */
  public static SubmenuId de(Long id) {
    return new SubmenuId(id);
  }

  public boolean esNuevo() {
    return valor == null;
  }

}