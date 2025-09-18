package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import pe.mef.sitfis.seguridad.adapter.outbound.auth.dto.KeycloakUsuarioResponse;

public record UsuarioDisponibleResponse(
    Boolean disponible,
    String mensaje,
    KeycloakUsuarioResponse usuario) {

}
