package pe.mef.sitfis.seguridad.application.port.outbound;

public interface VerificarUsuariosAsignadosPort {

  boolean existenUsuariosAsignados(Long rolGrupoId);

  int contarUsuariosAsignados(Long rolGrupoId);
}