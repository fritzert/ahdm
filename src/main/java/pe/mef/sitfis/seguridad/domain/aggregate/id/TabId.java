package pe.mef.sitfis.seguridad.domain.aggregate.id;

/**
 * Value Object que representa el identificador único de un Tab
 */
public record TabId(Long valor) {

  /**
   * Constructor que valida el ID del tab
   *
   * @param valor identificador numérico del tab
   * @throws IllegalArgumentException si el valor es nulo o menor o igual a cero
   */
  public TabId {
    if (valor == null || valor <= 0) {
      throw new IllegalArgumentException("El ID del tab debe ser un número positivo");
    }
  }

  public static TabId nuevo() {
    return new TabId(null);
  }

  /**
   * Crea un TabId a partir de un Long.
   *
   * @param id valor del identificador
   * @return nueva instancia de TabId
   */
  public static TabId de(Long id) {
    return new TabId(id);
  }

  public boolean esNuevo() {
    return valor == null;
  }

}
