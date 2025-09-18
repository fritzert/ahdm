package pe.mef.sitfis.seguridad.adapter.outbound.bd.projection;

import java.util.UUID;

public interface ListaRolGrupoMenuProjection {

  UUID getId();

  Long getRolGrupoId();

  UUID getListaId();

  Long getMenuId();

  String getMenuNombre();

  Long getSubmenuId();

  String getSubmenuNombre();

  Long getTabId();

  String getTabNombre();

  String getFechaModificacion();

}
