package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record RolGrupoFlagAsignarRecursosValue(Integer valor) {

  private static final Integer VALOR_MINIMO = 0;
  private static final Integer VALOR_MAXIMO = 1;

  /**
   * Constructor que valida el flagAsignarRecursos del rolGrupo.
   *
   * @param valor entero que representa el flagAsignarRecursos del rolGrupo
   * @throws IllegalArgumentException si el flagAsignarRecursos no cumple las reglas de negocio
   */
  public RolGrupoFlagAsignarRecursosValue {
    validarFlagAsignarRecursos(valor);
  }

  /**
   * Valida las reglas de negocio para el flagAsignarRecursos del rolGrupo.
   *
   * @param valor a validar
   * @throws IllegalArgumentException si el flagAsignarRecursos no es válido
   */
  private void validarFlagAsignarRecursos(Integer valor) {
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
