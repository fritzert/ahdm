package pe.mef.sitfis.seguridad.domain.exception;

public class RolGrupoNoPuedeEliminarseDomainException extends RuntimeException {

  public RolGrupoNoPuedeEliminarseDomainException(String message) {
    super(message);
  }

  public RolGrupoNoPuedeEliminarseDomainException(String message, Throwable cause) {
    super(message, cause);
  }
}
