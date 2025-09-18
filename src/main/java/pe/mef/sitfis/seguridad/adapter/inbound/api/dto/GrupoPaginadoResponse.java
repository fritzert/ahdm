package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.time.LocalDateTime;

public record GrupoPaginadoResponse(
    Long id,
    String nombre,
    String codigo,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
