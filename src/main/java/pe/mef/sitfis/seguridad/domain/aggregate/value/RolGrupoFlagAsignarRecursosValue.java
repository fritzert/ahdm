package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record RolGrupoFlagAsignarRecursosValue(Integer valor) {

  private static final Integer VALOR_MINIMO = 0;
  private static final Integer VALOR_MAXIMO = 1;

  /**
   * Valida las reglas de negocio para el flagAsignarRecursos del rolGrupo.
   *
   * @param valor a validar
   * @throws IllegalArgumentException si el flagAsignarRecursos no es válido
   */
  public RolGrupoFlagAsignarRecursosValue {
    if (valor == null) {
      throw new IllegalArgumentException("El flagAsignarRecursos del rolGrupo no puede ser nulo");
    }
    if (valor < VALOR_MINIMO) {
      throw new IllegalArgumentException(
          "El flagAsignarRecursos del rolGrupo debe ser mayor o igual a " + VALOR_MINIMO);
    }
    if (valor > VALOR_MAXIMO) {
      throw new IllegalArgumentException(
          "El flagAsignarRecursos del rolGrupo debe ser menor o igual a " + VALOR_MAXIMO);
    }
  }

}
