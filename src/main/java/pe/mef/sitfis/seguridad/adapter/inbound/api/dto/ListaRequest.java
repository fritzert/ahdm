package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import jakarta.validation.constraints.NotNull;

public record ListaRequest(
    @NotNull(message = "El valor no debe ser nulo")
    Long menuId,

    @NotNull(message = "El valor no debe ser nulo")
    Long submenuId,

    @NotNull(message = "El valor no debe ser nulo")
    Long tabId) {

}