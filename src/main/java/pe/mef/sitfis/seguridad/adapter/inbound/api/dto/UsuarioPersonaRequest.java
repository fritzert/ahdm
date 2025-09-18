package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;

public record UsuarioPersonaRequest(
    UUID id,
    PersonaRequest persona,
    String cuenta,
    String clave,
    int estado) {

}
