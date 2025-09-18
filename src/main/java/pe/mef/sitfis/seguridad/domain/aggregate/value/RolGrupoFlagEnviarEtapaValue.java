package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record RolGrupoFlagEnviarEtapaValue(Integer valor) {

  private static final Integer VALOR_MINIMO = 0;
  private static final Integer VALOR_MAXIMO = 1;

  /**
   * Constructor que valida el flagEnviarEtapa del rolGrupo.
   *
   * @param valor entero que representa el flagEnviarEtapa del rolGrupo
   * @throws IllegalArgumentException si el flagEnviarEtapa no cumple las reglas de negocio
   */
  public RolGrupoFlagEnviarEtapaValue {
    validarFlagEnviarEtapa(valor);
  }

  /**
   * Valida las reglas de negocio para el flagEnviarEtapa del rolGrupo.
   *
   * @param valor a validar
   * @throws IllegalArgumentException si el flagEnviarEtapa no es válido
   */
  private void validarFlagEnviarEtapa(Integer valor) {
    if (valor == null) {
      throw new IllegalArgumentException("El flagEnviarEtapa del rolGrupo no puede ser nulo");
    }
    if (valor < VALOR_MINIMO) {
      throw new IllegalArgumentException(
          "El flagEnviarEtapa del rolGrupo debe ser mayor o igual a " + VALOR_MINIMO);
    }
    if (valor > VALOR_MAXIMO) {
      throw new IllegalArgumentException(
          "El flagEnviarEtapa del rolGrupo debe ser menor o igual a " + VALOR_MAXIMO);
    }
  }

}
