package pe.mef.sitfis.seguridad.application.exception;

public class DatabaseException extends RuntimeException {

  public DatabaseException() {
  }

  public DatabaseException(String exception) {
    super(exception);
  }

}
