package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record TabComponenteValue(String valor) {

  private static final int LONGITUD_MINIMA = 2;
  private static final int LONGITUD_MAXIMA = 100;

  /**
   * Constructor que valida el componente del tab.
   *
   * @param valor cadena que representa el componente del tab
   * @throws IllegalArgumentException si el componente no cumple las reglas de negocio
   */
  public TabComponenteValue {
    validarComponente(valor);
  }

  /**
   * Valida las reglas de negocio para el componente del tab.
   *
   * @param componente cadena a validar
   * @throws IllegalArgumentException si el componente no es válido
   */
  private void validarComponente(String componente) {
    if (componente == null || componente.trim().isEmpty()) {
      throw new IllegalArgumentException("El componente del tab no puede estar vacío");
    }
    if (componente.trim().length() < LONGITUD_MINIMA) {
      throw new IllegalArgumentException(
          "El componente del tab debe tener al menos " + LONGITUD_MINIMA + " caracteres");
    }
    if (componente.trim().length() > LONGITUD_MAXIMA) {
      throw new IllegalArgumentException(
          "El componente del tab no puede exceder " + LONGITUD_MAXIMA + " caracteres");
    }
  }

  /**
   * Crea un TabComponenteValue a partir de un String.
   *
   * @param componente valor del componente
   * @return nueva instancia de TabComponenteValue
   */
  public static TabComponenteValue de(String componente) {
    return new TabComponenteValue(componente);
  }

  /**
   * Obtiene el valor formateado para mostrar.
   *
   * @return componente formateado
   */
  public String valorFormateado() {
    return valor.trim();
  }
}
