package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.List;
import java.util.UUID;

public record PersonaRequest(
    UUID id,
    String nombre,
    String apellidoPaterno,
    String apellidoMaterno,
    Long tipoPersonaId,
    List<ActualizarPersonaDocumentoRequest> personaDocumentos, 
    String correo,
    String celular,
    int flagVigente,
    int estado) {

}
