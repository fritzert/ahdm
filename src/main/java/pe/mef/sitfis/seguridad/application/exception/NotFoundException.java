package pe.mef.sitfis.seguridad.application.exception;

public class NotFoundException extends RuntimeException {

  public NotFoundException() {
  }

  public NotFoundException(String message) {
    super(message);
  }

}
