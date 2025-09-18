package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.LinkedHashSet;
import java.util.UUID;

public record GrupoRolMenuResponse(
    UUID id,
    Long grupoId,
    String grupo,
    LinkedHashSet<RolComboResponse> rol,
    LinkedHashSet<MenuResponse> menu,
    LinkedHashSet<SubMenuResponse> submenu,
    LinkedHashSet<TabResponse> tab) {

}

