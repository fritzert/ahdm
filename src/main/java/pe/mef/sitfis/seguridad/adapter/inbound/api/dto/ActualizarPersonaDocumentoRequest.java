package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarPersonaDocumentoRequest(
		Long id,
		UUID personaId,
		Long categoriaPersonaId,
	    TipoDocumentoAuditoriaResponse tipoDocumento,
	    @Size(min = 8, max = 11, message = "Maximo debe contener 11 digitos")
//	    @Pattern(regexp = "\\d{8}", message = "El valor solo debe contener digitos")
	    @NotBlank(message = "El valor no debe ser nulo, vacio o espacios en blanco")
	    String numeroDocumento,
	    Long estado
	    ) {

}

