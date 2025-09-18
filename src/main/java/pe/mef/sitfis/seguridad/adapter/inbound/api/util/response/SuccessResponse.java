package pe.mef.sitfis.seguridad.adapter.inbound.api.util.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.io.Serializable;
import java.time.LocalDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record SuccessResponse<T>(
    String mensaje,
    LocalDateTime fechaHora,
    Integer codigo,
    T data) implements Serializable {

  public SuccessResponse {
    mensaje = mensaje == null ? "Proceso ejecutado satisfactoriamente" : mensaje;
    fechaHora = fechaHora == null ? LocalDateTime.now() : fechaHora;
    codigo = codigo == null ? 200 : codigo;
  }

  public SuccessResponse(T data) {
    this("Proceso ejecutado satisfactoriamente", LocalDateTime.now(), 200, data);
  }

  public SuccessResponse(Integer codigo, String mensaje) {
    this(mensaje, LocalDateTime.now(), codigo, null);
  }

}
