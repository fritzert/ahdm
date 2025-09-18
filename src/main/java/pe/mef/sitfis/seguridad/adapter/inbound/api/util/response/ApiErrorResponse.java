package pe.mef.sitfis.seguridad.adapter.inbound.api.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record ApiErrorResponse(
    Integer codigo,
    String mensaje,
    LocalDateTime fechaHora,
    String ruta,
    List<ErrorValidacionResponse> errores
) implements Serializable {

  public ApiErrorResponse(HttpStatus httpStatus, String mensaje, String ruta) {
    this(httpStatus.value(), mensaje, LocalDateTime.now(), ruta, null);
  }

}
