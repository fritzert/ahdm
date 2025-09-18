package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;

public record UsuarioCopiarRolRequest(
    UUID idUsuarioSinRoles,
    UUID idUsuarioConRoles) {

}
