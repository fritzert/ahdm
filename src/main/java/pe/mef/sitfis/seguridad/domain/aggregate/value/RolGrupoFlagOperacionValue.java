package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record RolGrupoFlagOperacionValue(Integer valor) {

  private static final Integer VALOR_MINIMO = 0;
  private static final Integer VALOR_MAXIMO = 1;

  /**
   * Constructor que valida el flagOperacion del rolGrupo.
   *
   * @param valor entero que representa el flagOperacion del rolGrupo
   * @throws IllegalArgumentException si el flagOperacion no cumple las reglas de negocio
   */
  public RolGrupoFlagOperacionValue {
    validarFlagOperacion(valor);
  }

  /**
   * Valida las reglas de negocio para el flagOperacion del rolGrupo.
   *
   * @param valor a validar
   * @throws IllegalArgumentException si el flagOperacion no es válido
   */
  private void validarFlagOperacion(Integer valor) {
    if (valor == null) {
      throw new IllegalArgumentException("El flagOperacion del rolGrupo no puede ser nulo");
    }
    if (valor < VALOR_MINIMO) {
      throw new IllegalArgumentException(
          "El flagOperacion del rolGrupo debe ser mayor o igual a " + VALOR_MINIMO);
    }
    if (valor > VALOR_MAXIMO) {
      throw new IllegalArgumentException(
          "El flagOperacion del rolGrupo debe ser menor o igual a " + VALOR_MAXIMO);
    }
  }

}
