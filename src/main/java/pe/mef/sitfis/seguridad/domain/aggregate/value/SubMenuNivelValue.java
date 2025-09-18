package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record SubMenuNivelValue(Integer valor) {

  private static final int NIVEL_MINIMO = 1;

  public SubMenuNivelValue {
    if (valor == null) {
      throw new IllegalArgumentException("El nivel del menú no puede ser nulo");
    }
    if (valor < NIVEL_MINIMO) {
      throw new IllegalArgumentException(
          "El nivel del menú debe ser al menos " + NIVEL_MINIMO);
    }
  }

  /**
   * Crea un SubMenuNivelValue a partir de un Integer.
   *
   * @param nivel valor del nivel
   * @return nueva instancia de SubMenuNivelValue
   */
  public static SubMenuNivelValue de(Integer nivel) {
    return new SubMenuNivelValue(nivel);
  }

}
