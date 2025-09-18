package pe.mef.sitfis.seguridad.adapter.outbound.auth.dto;

public record KeycloakUsuarioResponse(
    String cuenta,
    String nombres,
    String apellidos) {

}