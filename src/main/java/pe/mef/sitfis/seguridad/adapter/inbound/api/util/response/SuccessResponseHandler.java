package pe.mef.sitfis.seguridad.adapter.inbound.api.util.response;

import java.time.LocalDateTime;
import org.springframework.http.ResponseEntity;

public class SuccessResponseHandler {

  private SuccessResponseHandler() {
    throw new UnsupportedOperationException("Clase Utilitaria");
  }

  public static <T> ResponseEntity<SuccessResponse<T>> SUCCESS() {
    SuccessResponse<T> result = new SuccessResponse<>(null);
    return ResponseEntity.status(result.codigo()).body(result);
  }

  public static <T> ResponseEntity<SuccessResponse<T>> SUCCESS(T data) {
    SuccessResponse<T> result = new SuccessResponse<>(data);
    return ResponseEntity.status(result.codigo()).body(result);
  }

  public static <T> ResponseEntity<SuccessResponse<T>> SUCCESS(Integer codigo, T data) {
    SuccessResponse<T> result = new SuccessResponse<>(
        "Proceso ejecutado satisfactoriamente",
        LocalDateTime.now(),
        codigo,
        data
    );
    return ResponseEntity.status(result.codigo()).body(result);
  }

  public static <T> ResponseEntity<SuccessResponse<T>> SUCCESS(Integer codigo, String mensaje) {
    SuccessResponse<T> result = new SuccessResponse<>(mensaje, LocalDateTime.now(), codigo, null);
    return ResponseEntity.status(result.codigo()).body(result);
  }

}
