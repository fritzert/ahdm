package pe.mef.sitfis.seguridad.adapter.inbound.api.spec;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.IObjectResponseDTO;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioCopiarRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoDTO;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoPrincipalRequest;

public interface UsuarioRolGrupoApi {

  @Operation(summary = "Obtiene todos los Usuarios relacionado a los Roles con sus Grupos", description = "returna 200 si el usuario no existe.", tags = {
      "SITFIS"})
  ResponseEntity<List<UsuarioRolGrupoDTO>> listarTodos();

  @Operation(summary = "Obtiene al Usuario relacionado a los Roles con sus Grupos", description = "returna 200 si el usuario no existe.", tags = {
      "SITFIS"})
  ResponseEntity<?> listarTodos(UUID id);

  @Operation(summary = "Registra el usuario relacionado a los Roles con sus Grupos", description = "returna 200 si el usuario no existe.", tags = {
      "SITFIS"})
  String save(UsuarioRolGrupoPrincipalRequest lstUsuarioRolGrupoDTO);

  @Operation(summary = "Registra el usuario copiando accesos de otro usuario", description = "returna 200 si el usuario no existe.", tags = {
      "SITFIS"})
  ResponseEntity<IObjectResponseDTO<String>> grabarCopiarRoles(
      UsuarioCopiarRolRequest usuarioCopiarRolDTO);
}
