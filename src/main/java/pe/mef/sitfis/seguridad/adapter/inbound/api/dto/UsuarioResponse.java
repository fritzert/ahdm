package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

public record UsuarioResponse(
    UUID id,
    UUID idpersona,
    String cuenta,
    String clave,
    Long grupoidprincipal,
    int intentoFallido,
    LocalDate fechaCaducidad,
    Long estado,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {

}
