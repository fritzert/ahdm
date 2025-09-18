package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record SubMenuOrdenValue(Integer valor) {

  private static final int ORDEN_MINIMO = 1;

  public SubMenuOrdenValue {
    if (valor == null) {
      throw new IllegalArgumentException("El orden del menú no puede ser nulo");
    }
    if (valor < ORDEN_MINIMO) {
      throw new IllegalArgumentException(
          "El orden del menú debe ser mayor o igual a " + ORDEN_MINIMO);
    }
  }

  public static SubMenuOrdenValue de(Integer orden) {
    return new SubMenuOrdenValue(orden);
  }

}