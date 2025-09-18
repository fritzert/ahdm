package pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes;

public class PathUtil {

  private PathUtil() {
    throw new UnsupportedOperationException("Clase Utilitaria");
  }

  public static final String GRUPOS_ENDPOINT = "/api/admin/grupos";
  public static final String GRUPOS_API_NAME = "GRUPOS";

  public static final String ROLES_ENDPOINT = "/api/admin/roles";
  public static final String ROLES_API_NAME = "ROLES";

  public static final String ROL_GRUPO_ENDPOINT = "/api/admin/rolesgrupos";
  public static final String ROL_GRUPO_API_NAME = "ROL GRUPO";

  public static final String TAB_ENDPOINT = "/api/admin/tab";
  public static final String TAB_API_NAME = "TAB";

  public static final String MENU_ENDPOINT = "/api/admin/menu";
  public static final String MENU_API_NAME = "MENU";

  public static final String SUBMENU_ENDPOINT = "/api/admin/submenu";
  public static final String SUBMENU_API_NAME = "SUBMENU";

  public static final String LISTA_ROL_GRUPO_ENDPOINT = "/api/admin/listas-roles-grupos";
  public static final String LISTA_ROL_GRUPO_API_NAME = "LISTA ROL GRUPO";

  public static final String USUARIO_ROL_GRUPO_ENDPOINT = "/api/admin/usuariosrolesgrupos-service";
  public static final String USUARIO_ROL_GRUPO_API_NAME = "USUARIO ROL GRUPO";


  public static final String BASE = "/v1/sitfis-seguridad-service";

  public static final String GRUPOS = BASE + "/grupos";
  public static final String ROLES = BASE + "/roles";
  public static final String ROLES_GRUPOS = BASE + "/roles-grupos";
  public static final String LISTAS_ROLES_GRUPOS = BASE + "/listas-roles-grupos";
  public static final String TABS = BASE + "/tabs";
  public static final String MENUS = BASE + "/menus";
  public static final String SUBMENUS = BASE + "/submenus";
  public static final String USUARIOS_ROLES_GRUPOS = BASE + "/usuarios-roles-grupos";
}
