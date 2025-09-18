package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record ListaRolGrupoRequest(
    UUID id,
    ListaRecord listaId,
    RolGrupoRecord rolGrupoId) {

  public record ListaRecord(
      @NotNull(message = "[listaId] No debe ser nulo")
      UUID id) {

  }

  public record RolGrupoRecord(
      @NotNull(message = "[rolGrupoId] No debe ser nulo")
      Long id) {

  }

}
