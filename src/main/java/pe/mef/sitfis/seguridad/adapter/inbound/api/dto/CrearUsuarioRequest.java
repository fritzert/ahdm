package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.Date;

import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.CrearPersonaRequest;

public record CrearUsuarioRequest(
    CrearPersonaRequest persona,
    String cuenta,
    String clave,
    Date fechaCaducidad,
    int estado) {

}
