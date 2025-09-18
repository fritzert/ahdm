package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.time.LocalDateTime;

public record RolPaginadoResponse(
    Long id,
    String nombre,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
