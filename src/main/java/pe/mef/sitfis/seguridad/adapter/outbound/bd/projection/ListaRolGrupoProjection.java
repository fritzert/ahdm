package pe.mef.sitfis.seguridad.adapter.outbound.bd.projection;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ListaRolGrupoProjection {

  UUID getId();

  Long getRolGrupoId();

  Long getRolId();

  String getRolNombre();

  Long getGrupoId();

  String getGrupoNombre();

  String getUsuarioModificacion();

  LocalDateTime getFechaModificacion();
}
