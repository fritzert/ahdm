package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record MenuNombreValue(String valor) {

  private static final int LONGITUD_MINIMA = 2;
  private static final int LONGITUD_MAXIMA = 100;

//  /**
//   * Constructor que valida el nombre del menu.
//   *
//   * @param valor cadena que representa el nombre del menu
//   * @throws IllegalArgumentException si el nombre no cumple las reglas de negocio
//   */
//  public MenuNombreValue {
//    validarNombre(valor);
//  }

  public MenuNombreValue {
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

//  /**
//   * Verifica si el nombre contiene el criterio de busqueda.
//   *
//   * @param criterio texto a buscar
//   * @return verdadero si el nombre contiene el criterio
//   */
//  public boolean contiene(String criterio) {
//    if (criterio == null) {
//      return true;
//    }
//    return valor.toUpperCase().contains(criterio.trim().toUpperCase());
//  }

  /**
   * Crea un MenuNombreValue a partir de un String.
   *
   * @param nombre valor del nombre
   * @return nueva instancia de MenuNombreValue
   */
//  public static MenuNombreValue de(String nombre) {
//    return new MenuNombreValue(nombre);
//  }

  /**
   * Obtiene el valor formateado para mostrar.
   *
   * @return nombre formateado
   */
  public String valorFormateado() {
    return valor.trim();
  }
}