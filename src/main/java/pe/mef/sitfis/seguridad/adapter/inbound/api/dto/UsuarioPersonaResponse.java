package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.PersonaResponse;

public record UsuarioPersonaResponse(
    UUID id,
    PersonaResponse persona,
    String cuenta,
    String clave,
    int estado) {

}
