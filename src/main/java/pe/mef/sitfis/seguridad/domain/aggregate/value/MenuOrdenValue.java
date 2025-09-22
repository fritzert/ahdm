package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record MenuOrdenValue(Integer valor) {

  private static final int ORDEN_MINIMO = 1;
  private static final int ORDEN_MAXIMO = 999;

  public MenuOrdenValue {
    if (valor == null) {
      throw new IllegalArgumentException("El orden del menú no puede ser nulo");
    }
    if (valor < ORDEN_MINIMO) {
      throw new IllegalArgumentException(
          "El orden del menú debe ser mayor o igual a " + ORDEN_MINIMO);
    }
    if (valor > ORDEN_MAXIMO) {
      throw new IllegalArgumentException("El orden del menú no puede ser mayor a " + ORDEN_MAXIMO);
    }
  }

  /**
   * Crea un MenuOrdenValue a partir de un Integer.
   *
   * @param orden valor del orden
   * @return nueva instancia de MenuOrdenValue
   */
//  public static MenuOrdenValue de(Integer orden) {
//    return new MenuOrdenValue(orden);
//  }

}