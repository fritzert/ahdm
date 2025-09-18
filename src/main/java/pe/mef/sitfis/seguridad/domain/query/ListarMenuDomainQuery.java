package pe.mef.sitfis.seguridad.domain.query;

public record ListarMenuDomainQuery(String criterioNombre) {

  public ListarMenuDomainQuery {
    if (criterioNombre != null && !criterioNombre.trim().isEmpty()) {
      criterioNombre = "%" + criterioNombre.trim().toUpperCase() + "%";
    } else {
      criterioNombre = null;
    }
  }

  public static ListarMenuDomainQuery sinFiltros() {
    return new ListarMenuDomainQuery(null);
  }

  public static ListarMenuDomainQuery conNombre(String nombre) {
    return new ListarMenuDomainQuery(nombre);
  }
}
