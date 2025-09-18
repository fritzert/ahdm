package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record TabOrdenValue(Integer valor) {

  private static final int ORDEN_MINIMO = 1;
  private static final int ORDEN_MAXIMO = 999;

  /**
   * Constructor que valida el orden del tab.
   *
   * @param valor número entero que representa el orden del tab
   * @throws IllegalArgumentException si el orden no cumple las reglas de negocio
   */
  public TabOrdenValue {
    validarOrden(valor);
  }

  /**
   * Valida las reglas de negocio para el orden del tab.
   *
   * @param orden valor a validar
   * @throws IllegalArgumentException si el orden no es válido
   */
  private void validarOrden(Integer orden) {
    if (orden == null) {
      throw new IllegalArgumentException("El orden del tab no puede ser nulo");
    }
    if (orden < ORDEN_MINIMO) {
      throw new IllegalArgumentException(
          "El orden del tab debe ser mayor o igual a " + ORDEN_MINIMO);
    }
    if (orden > ORDEN_MAXIMO) {
      throw new IllegalArgumentException("El orden del tab no puede ser mayor a " + ORDEN_MAXIMO);
    }
  }

  /**
   * Crea un TabOrdenValue a partir de un Integer.
   *
   * @param orden valor del orden
   * @return nueva instancia de TabOrdenValue
   */
  public static TabOrdenValue de(Integer orden) {
    return new TabOrdenValue(orden);
  }
}
