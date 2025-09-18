package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

public record CrearListaRolGrupoRequest(

    @NotNull(message = "El valor no debe ser nulo")
    Long rolGrupoId,

    @Valid
    ListaRequest lista) {

}

