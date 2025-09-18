package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.PersonaComboResponse;

public record UsuarioComboResponse(
    UUID id,
    PersonaComboResponse persona,
    String cuenta) {

}
