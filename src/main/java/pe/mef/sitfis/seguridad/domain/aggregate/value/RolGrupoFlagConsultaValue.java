package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record RolGrupoFlagConsultaValue(Integer valor) {

  private static final Integer VALOR_MINIMO = 0;
  private static final Integer VALOR_MAXIMO = 1;

  /**
   * Constructor que valida el flagConsulta del rolGrupo.
   *
   * @param valor entero que representa el flagConsulta del rolGrupo
   * @throws IllegalArgumentException si el flagConsulta no cumple las reglas de negocio
   */
  public RolGrupoFlagConsultaValue {
    validarFlagConsulta(valor);
  }

  /**
   * Valida las reglas de negocio para el flagConsulta del rolGrupo.
   *
   * @param valor a validar
   * @throws IllegalArgumentException si el flagConsulta no es válido
   */
  private void validarFlagConsulta(Integer valor) {
    if (valor == null) {
      throw new IllegalArgumentException("El flagConsulta del rolGrupo no puede ser nulo");
    }
    if (valor < VALOR_MINIMO) {
      throw new IllegalArgumentException(
          "El flagConsulta del rolGrupo debe ser mayor o igual a " + VALOR_MINIMO);
    }
    if (valor > VALOR_MAXIMO) {
      throw new IllegalArgumentException(
          "El flagConsulta del rolGrupo debe ser menor o igual a " + VALOR_MAXIMO);
    }
  }

}
