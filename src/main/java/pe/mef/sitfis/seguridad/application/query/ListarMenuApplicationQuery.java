package pe.mef.sitfis.seguridad.application.query;

public record ListarMenuApplicationQuery(String nombre) {

  public static ListarMenuApplicationQuery sinFiltros() {
    return new ListarMenuApplicationQuery(null);
  }

  public static ListarMenuApplicationQuery conNombre(String nombre) {
    return new ListarMenuApplicationQuery(nombre);
  }

}
