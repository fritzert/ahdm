package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.LinkedHashSet;
import java.util.UUID;

public record UsuarioRolGrupoMenuResponse(
    UUID usuarioId,
    UUID personaId,
    String cuenta,
    Long grupoidprincipal,
    LinkedHashSet<GrupoRolMenuResponse> grupoRolMenu) {

}




