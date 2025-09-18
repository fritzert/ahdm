package pe.mef.sitfis.seguridad.domain.exception;

public class RolGrupoInvalidoDomainException extends RuntimeException {

  public RolGrupoInvalidoDomainException(String message) {
    super(message);
  }

  public RolGrupoInvalidoDomainException(String message, Throwable cause) {
    super(message, cause);
  }
}
