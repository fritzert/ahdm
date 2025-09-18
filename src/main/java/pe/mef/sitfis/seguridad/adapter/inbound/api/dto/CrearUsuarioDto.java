package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.Date;
import java.util.UUID;

public record CrearUsuarioDto(
    UUID personaId,
    String cuenta,
    String clave,
    Date fechaCaducidad,
    int estado) {

}
