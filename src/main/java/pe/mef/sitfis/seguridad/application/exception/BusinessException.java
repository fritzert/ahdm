package pe.mef.sitfis.seguridad.application.exception;

public class BusinessException extends RuntimeException {

  public BusinessException() {
  }

  public BusinessException(String exception) {
    super(exception);
  }

}
