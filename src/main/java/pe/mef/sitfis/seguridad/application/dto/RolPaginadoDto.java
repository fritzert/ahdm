package pe.mef.sitfis.seguridad.application.dto;

import java.time.LocalDateTime;

public record RolPaginadoDto(
    Long id,
    String nombre,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
