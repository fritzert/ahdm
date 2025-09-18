package pe.mef.sitfis.seguridad.domain.aggregate.id;

/**
 * Value Object que representa el identificador unico de un Grupo
 *
 */
public record GrupoId(Long valor) {

  /**
   * Constructor que valida el ID del grupo
   *
   * @param valor identificador numerico del grupo
   * @throws IllegalArgumentException si el valor es nulo o menor o igual a cero
   */
  public GrupoId {
    if (valor == null || valor <= 0) {
      throw new IllegalArgumentException("El ID del grupo debe ser un número positivo");
    }
  }

  /**
   * Crea un GrupoId a partir de un Long.
   *
   * @param id valor del identificador
   * @return nueva instancia de GrupoId
   */
  public static GrupoId de(Long id) {
    return new GrupoId(id);
  }

}
