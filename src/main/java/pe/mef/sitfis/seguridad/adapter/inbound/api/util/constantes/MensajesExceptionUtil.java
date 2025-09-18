package pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes;

public class MensajesExceptionUtil {

  private MensajesExceptionUtil() {
    throw new UnsupportedOperationException("Clase Utilitaria");
  }

  public static final String CREDENCIALES_INCORRECTAS = "Credenciales incorrectas: ";

  public static final String TRANSACCION_NO_PERMITIDA = "Transacción no permitida: ";

  public static final String PETICION_INCORRECTA = "Peticion incorrecta: ";

  public static final String RECURSO_NO_ENCONTRADO = "Recurso no encontrado: ";

  public static final String DETALLE_ERRORES = "Agregue/corrija sus parámetros. Revise los errores";

  public static final String FALTA_PARAMETRO = "Falta el parámetro requerido: ";

  public static final String ERROR_AUTH_EXTERNO = "Error de autenticación con el servicio externo";

}
