package pe.mef.sitfis.seguridad.adapter.outbound.bd.projection;

public interface UsuarioDatosLoginProjection {

  String getUsuarioId();

  String getCuenta();

  String getPersonaId();

  Long getGrupoPrincipalId();
  
  String getUsuarioRolGrupoId();

  Long getRolGrupoId();

  Long getGrupoId();

  String getNombreGrupo();

  Long getRolId();

  String getNombreRol();

  Long getMenuId();

  String getNombreMenu();
  
  String getRutaMenu();

  Integer getOrdenMenu();

  Long getSubmenuId();

  String getNombreSubmenu();
  
  String getRutaSubmenu();

  Integer getNivelSubmenu();

  Integer getOrdenSubmenu();

  Long getTabId();

  String getNombreTab();
  
  String getComponenteTab();

  Integer getOrdenTab();
}
