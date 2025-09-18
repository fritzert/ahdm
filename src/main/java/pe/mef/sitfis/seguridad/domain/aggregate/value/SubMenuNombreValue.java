package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record SubMenuNombreValue(String valor) {

  private static final int LONGITUD_MINIMA = 2;
  private static final int LONGITUD_MAXIMA = 100;

  public SubMenuNombreValue {
    if (valor == null || valor.trim().isEmpty()) {
      throw new IllegalArgumentException("El nombre del menú no puede estar vacío");
    }
    if (valor.trim().length() < LONGITUD_MINIMA) {
      throw new IllegalArgumentException(
          "El nombre del menú debe tener al menos " + LONGITUD_MINIMA + " caracteres");
    }
    if (valor.trim().length() > LONGITUD_MAXIMA) {
      throw new IllegalArgumentException(
          "El nombre del menú no puede exceder " + LONGITUD_MAXIMA + " caracteres");
    }
  }

  /**
   * Crea un SubMenuNombreValue a partir de un String.
   *
   * @param nombre valor del nombre
   * @return nueva instancia de MenuNombreValue
   */
  public static SubMenuNombreValue de(String nombre) {
    return new SubMenuNombreValue(nombre);
  }

  /**
   * Obtiene el valor formateado para mostrar.
   *
   * @return nombre formateado
   */
  public String valorFormateado() {
    return valor.trim();
  }
}