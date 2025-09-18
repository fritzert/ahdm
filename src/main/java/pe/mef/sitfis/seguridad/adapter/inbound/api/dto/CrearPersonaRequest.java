package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.List;

public record CrearPersonaRequest(
	    String nombre,
	    String apellidoPaterno,
	    String apellidoMaterno,
	    Long tipoPersonaId,
	    List<CrearPersonaDocumentoRequest> personaDocumentos,
	    String correo,
	    String celular,
	    Long flagVigente,
	    Long estado) {

	}
