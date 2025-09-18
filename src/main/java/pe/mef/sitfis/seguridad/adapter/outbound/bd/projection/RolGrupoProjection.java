package pe.mef.sitfis.seguridad.adapter.outbound.bd.projection;

import java.time.LocalDateTime;

public interface RolGrupoProjection {

  Long getId();

  Long getRolId();

  String getRolNombre();

  Long getGrupoId();

  String getFlagRestriccion();

  String getFlagConsulta();

  String getFlagOperacion();

  String getFlagAsignarRecursos();

  String getFlagEnviarBandeja();

  String getFlagEnviarEtapa();

  String getFlagAdjuntarArchivo();

  String getUsuarioModificacion();

  LocalDateTime getFechaModificacion();

}
