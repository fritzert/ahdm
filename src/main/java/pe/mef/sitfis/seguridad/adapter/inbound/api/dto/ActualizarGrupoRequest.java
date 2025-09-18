package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ActualizarGrupoRequest(
    @NotBlank(message = "El valor no debe ser nulo, vacio o espacios en blanco")
    @Size(max = 100, message = "Máximo 100 caracteres")
    String nombre,

    @NotBlank(message = "El valor no debe ser nulo, vacio o espacios en blanco")
    @Size(max = 20, message = "Máximo 20 caracteres")
    String codigo) {

}
