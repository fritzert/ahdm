package pe.mef.sitfis.seguridad.adapter.inbound.api.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoginRolesMenuDTO {

  private UUID idUsuario;
  private String usuario;
  private UUID idPersona;
  private Long grupoIdPrincipal;
  private Long idRolGrupo;
  private Long idGrupo;
  private String grupo;
  private Long idRol;
  private String rol;
  private Long idMenu;
  private String menu;
  private Integer ordenMenu;
  private Long idSubmenu;
  private String submenu;
  private Integer nivel;
  private Integer ordenSubmenu;
  private Long idTab;
  private String tab;
  private Integer ordenTab;
}