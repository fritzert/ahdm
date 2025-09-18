package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record TabNombreValue(String valor) {

  private static final int LONGITUD_MINIMA = 2;
  private static final int LONGITUD_MAXIMA = 100;

  /**
   * Constructor que valida el nombre del tab.
   *
   * @param valor cadena que representa el nombre del tab
   * @throws IllegalArgumentException si el nombre no cumple las reglas de negocio
   */
  public TabNombreValue {
    validarNombre(valor);
  }

  /**
   * Valida las reglas de negocio para el nombre del tab.
   *
   * @param nombre cadena a validar
   * @throws IllegalArgumentException si el nombre no es válido
   */
  private void validarNombre(String nombre) {
    if (nombre == null || nombre.trim().isEmpty()) {
      throw new IllegalArgumentException("El nombre del tab no puede estar vacío");
    }
    if (nombre.trim().length() < LONGITUD_MINIMA) {
      throw new IllegalArgumentException(
          "El nombre del tab debe tener al menos " + LONGITUD_MINIMA + " caracteres");
    }
    if (nombre.trim().length() > LONGITUD_MAXIMA) {
      throw new IllegalArgumentException(
          "El nombre del tab no puede exceder " + LONGITUD_MAXIMA + " caracteres");
    }
  }

  /**
   * Verifica si el nombre contiene el criterio de búsqueda.
   *
   * @param criterio texto a buscar
   * @return verdadero si el nombre contiene el criterio
   */
  public boolean contiene(String criterio) {
    if (criterio == null) {
      return true;
    }
    return valor.toUpperCase().contains(criterio.trim().toUpperCase());
  }

  /**
   * Crea un TabNombreValue a partir de un String.
   *
   * @param nombre valor del nombre
   * @return nueva instancia de TabNombreValue
   */
  public static TabNombreValue de(String nombre) {
    return new TabNombreValue(nombre);
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
