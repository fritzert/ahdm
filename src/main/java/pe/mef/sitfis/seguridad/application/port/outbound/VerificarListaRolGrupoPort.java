package pe.mef.sitfis.seguridad.application.port.outbound;

public interface VerificarListaRolGrupoPort {

  boolean existenListasAsignadas(Long rolGrupoId);

  int contarListasAsignadas(Long rolGrupoId);
}
