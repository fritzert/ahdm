package pe.mef.sitfis.seguridad.domain.aggregate.value;

public record RolGrupoFlagAdjuntarArchivoValue(Integer valor) {

  private static final Integer VALOR_MINIMO = 0;
  private static final Integer VALOR_MAXIMO = 1;

  /**
   * Constructor que valida el flagAdjuntarArchivo del rolGrupo.
   *
   * @param valor entero que representa el flagAdjuntarArchivo del rolGrupo
   * @throws IllegalArgumentException si el flagRestriccion no cumple las reglas de negocio
   */
  public RolGrupoFlagAdjuntarArchivoValue {
    validarFlagAdjuntarArchivo(valor);
  }

  /**
   * Valida las reglas de negocio para el flagAdjuntarArchivo del rolGrupo.
   *
   * @param valor a validar
   * @throws IllegalArgumentException si el flagAdjuntarArchivo no es válido
   */
  private void validarFlagAdjuntarArchivo(Integer valor) {
    if (valor == null) {
      throw new IllegalArgumentException("El flagAdjuntarArchivo del rolGrupo no puede ser nulo");
    }
    if (valor < VALOR_MINIMO) {
      throw new IllegalArgumentException(
          "El flagAdjuntarArchivo del rolGrupo debe ser mayor o igual a " + VALOR_MINIMO);
    }
    if (valor > VALOR_MAXIMO) {
      throw new IllegalArgumentException(
          "El flagAdjuntarArchivo del rolGrupo debe ser menor o igual a " + VALOR_MAXIMO);
    }
  }

}
