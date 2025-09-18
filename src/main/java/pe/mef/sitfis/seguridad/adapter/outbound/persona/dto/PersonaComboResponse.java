package pe.mef.sitfis.seguridad.adapter.outbound.persona.dto;

import java.util.UUID;

public record PersonaComboResponse(
    UUID id,
    String nombre,
    String apellidoPaterno,
    String apellidoMaterno) {

}
