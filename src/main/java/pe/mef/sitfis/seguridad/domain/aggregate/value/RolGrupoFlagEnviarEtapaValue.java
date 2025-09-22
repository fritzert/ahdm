package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record RolGrupoFlagEnviarEtapaValue(Integer valor) {

  private static final Integer VALOR_MINIMO = 0;
  private static final Integer VALOR_MAXIMO = 1;

  public RolGrupoFlagEnviarEtapaValue {
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
