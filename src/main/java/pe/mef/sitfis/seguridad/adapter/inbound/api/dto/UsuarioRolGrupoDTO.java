package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioRolGrupoDTO {

  private UUID id;
  private UUID usuarioId;
  private String usuarioNombre;
  private Long rolGrupoId;
  private String rolNombre;
  private String grupoNombre;
  private int estado;
  private String usucre;
  private LocalDateTime feccre;
  private String usumod;
  private LocalDateTime fecmod;
}
