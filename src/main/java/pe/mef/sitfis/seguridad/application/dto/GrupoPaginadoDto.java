package pe.mef.sitfis.seguridad.application.dto;

import java.time.LocalDateTime;

public record GrupoPaginadoDto(
    Long id,
    String nombre,
    String codigo,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
