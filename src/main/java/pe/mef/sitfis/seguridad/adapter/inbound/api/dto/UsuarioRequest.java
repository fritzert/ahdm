package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;

public record UsuarioRequest(
    UUID id,
    UUID personaId,
    String cuenta,
    String clave,
    Long grupoPrincipalId,
    int estado) {

}
