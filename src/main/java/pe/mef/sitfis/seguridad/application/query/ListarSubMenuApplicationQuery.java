package pe.mef.sitfis.seguridad.application.query;

public record ListarSubMenuApplicationQuery(String nombre, Long menuId) {

  public static ListarSubMenuApplicationQuery de(String nombre, Long menuId) {
    return new ListarSubMenuApplicationQuery(nombre, menuId);
  }

}
