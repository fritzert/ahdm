package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record RolGrupoFlagRestriccionValue(Integer valor) {

  private static final Integer VALOR_MINIMO = 0;
  private static final Integer VALOR_MAXIMO = 1;

  /**
   * Constructor que valida el flagRestriccion del rolGrupo.
   *
   * @param valor entero que representa el flagRestriccion del rolGrupo
   * @throws IllegalArgumentException si el flagRestriccion no cumple las reglas de negocio
   */
  public RolGrupoFlagRestriccionValue {
    validarFlagRestriccion(valor);
  }

  /**
   * Valida las reglas de negocio para el flagRestriccion del rolGrupo.
   *
   * @param valor a validar
   * @throws IllegalArgumentException si el flagRestriccion no es válido
   */
  private void validarFlagRestriccion(Integer valor) {
    if (valor == null) {
      throw new IllegalArgumentException("El flagRestriccion del rolGrupo no puede ser nulo");
    }
    if (valor < VALOR_MINIMO) {
      throw new IllegalArgumentException(
          "El flagRestriccion del rolGrupo debe ser mayor o igual a " + VALOR_MINIMO);
    }
    if (valor > VALOR_MAXIMO) {
      throw new IllegalArgumentException(
          "El flagRestriccion del rolGrupo debe ser menor o igual a " + VALOR_MAXIMO);
    }
  }
//
//  /**
//   * Verifica si el nombre contiene el criterio de búsqueda.
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

//  /**
//   * Crea un TabNombreValue a partir de un String.
//   *
//   * @param nombre valor del nombre
//   * @return nueva instancia de TabNombreValue
//   */
//  public static TabNombreValue de(int nombre) {
//    return new TabNombreValue(nombre);
//  }
//
//  /**
//   * Obtiene el valor formateado para mostrar.
//   *
//   * @return nombre formateado
//   */
//  public String getValorFormateado() {
//    return valor.trim();
//  }
}
