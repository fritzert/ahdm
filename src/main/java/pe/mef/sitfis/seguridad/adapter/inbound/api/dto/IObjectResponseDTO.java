package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IObjectResponseDTO<T> {

  private String message;
  private String status;
  private String codResponse;
  private T data;
}
