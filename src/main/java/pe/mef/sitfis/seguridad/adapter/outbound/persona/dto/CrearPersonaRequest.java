package pe.mef.sitfis.seguridad.adapter.outbound.persona.dto;

import java.util.List;

import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearPersonaDocumentoRequest;

public record CrearPersonaRequest(
    String nombre,
    String apellidoPaterno,
    String apellidoMaterno,
    Long tipoPersonaId,
    List<CrearPersonaDocumentoRequest> personaDocumentos, 
    String correo,
    String celular,
    int flagVigente,
    int estado) {

}
