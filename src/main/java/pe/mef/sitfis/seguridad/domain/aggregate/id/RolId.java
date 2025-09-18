package pe.mef.sitfis.seguridad.domain.aggregate.id;

/**
 * Value Object que representa el identificador unico de un Rol
 *
 */
public record RolId(Long valor) {

  /**
   * Constructor que valida el ID del rol
   *
   * @param valor identificador numerico del rol
   * @throws IllegalArgumentException si el valor es nulo o menor o igual a cero
   */
  public RolId {
    if (valor == null || valor <= 0) {
      throw new IllegalArgumentException("El ID del rol debe ser un número positivo");
    }
  }

  /**
   * Crea un RolId a partir de un Long.
   *
   * @param id valor del identificador
   * @return nueva instancia de RolId
   */
  public static RolId de(Long id) {
    return new RolId(id);
  }

}
