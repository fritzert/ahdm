package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.List;
import java.util.UUID;

public record EliminarMultipleUsuarioRolGrupoRequest(
    List<UUID> uuids) {

}
