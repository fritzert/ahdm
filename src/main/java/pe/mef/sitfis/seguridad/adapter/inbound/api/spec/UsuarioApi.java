package pe.mef.sitfis.seguridad.adapter.inbound.api.spec;

import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.IObjectResponseDTO;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioPersonaResponse;


public interface UsuarioApi {

  @Operation(summary = "Obtiene todos los Usuarios paginados", description = "Retorna 200 con la lista paginada de usuarios registrados en el SITFIS.", tags = {
      "SITFIS"})
  ResponseEntity<Map<String, Object>> findAllUsuariosPaginacion(Integer page, Integer size);


  @Operation(summary = "Obtiene todos los Usuarios que no tiene asociados roles", description = "Retorna 200 con la lista paginada de usuarios registrados en el SITFIS.", tags = {
      "SITFIS"})
  ResponseEntity<List<UsuarioComboResponse>> findAllUsuarioSinRoles(String usuario);

  @Operation(summary = "Obtiene todos los Usuarios que si tienen asociados roles", description = "Retorna 200 con la lista paginada de usuarios registrados en el SITFIS.", tags = {
      "SITFIS"})
  ResponseEntity<List<UsuarioComboResponse>> findAllUsuarioConRoles(String usuario);

  @Operation(summary = "Obtiene los Usuarios filtrados y paginados", description = "Retorna 200 con la lista paginada de usuarios registrados en el SITFIS según los filtros.", tags = {
      "SITFIS"})
  ResponseEntity<Map<String, Object>> findByLikeUsuario(String usuario, String estado,
      String nombre, Long tipoDocumentoId, String numeroDocumento,
      Integer page,
      Integer size);

  @Operation(summary = "Registra al Usuario y los datos de la Persona", description = "Retorna 201 si se registró correctamente. Retorna 409 si ya existe.", tags = {
      "SITFIS"})
  ResponseEntity<IObjectResponseDTO<UsuarioPersonaResponse>> save(
      UsuarioPersonaResponse usuarioPersonaResponse);

  @Operation(summary = "Edita al Usuario y los datos de la Persona", description = "Retorna 200 si el usuario existe y fue actualizado.", tags = {
      "SITFIS"})
  ResponseEntity<IObjectResponseDTO<UsuarioPersonaResponse>> update(UUID id,
      UsuarioPersonaResponse usuarioPersonaResponse);

}
