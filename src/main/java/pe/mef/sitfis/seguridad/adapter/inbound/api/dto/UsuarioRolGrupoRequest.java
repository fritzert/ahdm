package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public record UsuarioRolGrupoRequest(
    UUID id,
    UsuarioRecord usuarioId,
    RolGrupoRecord rolGrupoId) {

  public record UsuarioRecord(
      @NotNull(message = "[usuarioId] No debe ser nulo")
      UUID id) {

  }

  public record RolGrupoRecord(
      @NotNull(message = "[rolGrupoId] No debe ser nulo")
      Long id) {

  }

}