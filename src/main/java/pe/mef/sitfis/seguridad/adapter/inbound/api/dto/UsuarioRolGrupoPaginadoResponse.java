package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record UsuarioRolGrupoPaginadoResponse(
    UUID id,
    String nombreUsuario,
    String tipoDocumento,
    String numeroDocumento,
    Long estado,
    String usuarioModificacion,
    LocalDateTime fechaModificacion,
    List<GrupoRecord> grupos) {

  public record GrupoRecord(
      UUID usuarioRolGrupoId,
      Long id,
      String nombre,
      List<RolRecord> roles
  ) {

  }

  public record RolRecord(
      UUID usuarioRolGrupoId,
      Long id,
      String nombre
  ) {

  }
}
