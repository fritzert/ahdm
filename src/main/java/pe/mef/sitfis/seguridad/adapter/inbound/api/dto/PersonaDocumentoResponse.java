package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;

public record PersonaDocumentoResponse(
		Long id,
		UUID personaId,
		Long categoriaPersonaId,
	    TipoDocumentoAuditoriaResponse tipoDocumento,
	    String numeroDocumento,
	    Integer estado
		) {
}
