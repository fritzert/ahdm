package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.List;

public record ActualizarPersonaRequest(
		List<ActualizarPersonaDocumentoRequest> personaDocumentos,
		String celular,
		int estado) {

}
