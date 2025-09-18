package pe.mef.sitfis.seguridad.adapter.inbound.api.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolGrupoInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolGrupoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.ApiErrorResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;

@Tag(name = "RolGrupoController", description = "API REST para la gestión de rolGrupo.")
public interface RolGrupoApi {

  @SuppressWarnings("unused")
  @Operation(
      summary = "Buscar rolGrupos por grupo ID",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = RolGrupoResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados con los filtros aplicados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. El formato o valor del ID proporcionado es inválido.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<RolGrupoResponse>>> listarPorGrupoId(Long grupoId);

  //  @SuppressWarnings("unused")
  @Operation(
      summary = "Crear un rolGrupo",
      responses = {
          @ApiResponse(responseCode = "201", description = "Creación exitosa. El dato fue creado satisfactoriamente.", content = @Content(schema = @Schema(implementation = RolGrupoInfoResponse.class))),
          @ApiResponse(responseCode = "400", description = "Petición incorrecta. Los datos proporcionados no son válidos o están incompletos.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "409", description = "Conflicto al crear el recurso. Ya existe un registro con datos similares.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<RolGrupoInfoResponse>> crear(
      @RequestBody @Valid CrearRolGrupoRequest request);

  @Operation(
      summary = "Actualizar un rolGrupo",
      responses = {
          @ApiResponse(responseCode = "200", description = "Actualización exitosa. El dato fue actualizado satisfactoriamente.", content = @Content(schema = @Schema(implementation = RolGrupoInfoResponse.class))),
          @ApiResponse(responseCode = "400", description = "Petición incorrecta. Los datos proporcionados no son válidos o están incompletos.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "409", description = "Conflicto al crear el recurso. Ya existe un registro con datos similares.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<RolGrupoInfoResponse>> actualizar(
      @PathVariable("rolGrupoId") Long rolGrupoId,
      @RequestBody @Valid ActualizarRolGrupoRequest request);

  @Operation(
      summary = "Eliminar un rolGrupo por grupo ID",
      responses = {
          @ApiResponse(responseCode = "204", description = "Eliminación satisfactoria. El registro fue eliminado correctamente.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. El formato o valor del ID proporcionado es inválido.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Dato no encontrado para el ID proporcionado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Void>> eliminar(Long grupoId);

}
