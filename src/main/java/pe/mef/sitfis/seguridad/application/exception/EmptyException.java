package pe.mef.sitfis.seguridad.application.exception;

public class EmptyException extends RuntimeException {

  public EmptyException() {
  }

  public EmptyException(String message) {
    super(message);
  }

}
