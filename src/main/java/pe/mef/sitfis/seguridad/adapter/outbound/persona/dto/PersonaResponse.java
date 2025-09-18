package pe.mef.sitfis.seguridad.adapter.outbound.persona.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.PersonaDocumentoResponse;

public record PersonaResponse(
    UUID id,
    String nombre,
    String apellidoPaterno,
    String apellidoMaterno,
    Long tipoPersonaId,
    List<PersonaDocumentoResponse> personaDocumentos,
    String correo,
    String celular,
    int flagVigente,
    int estado,
    String usuarioCreacion,
    LocalDateTime fechaCreacion,
    String usuarioModificacion,
    LocalDateTime fechaModificacion) {
}