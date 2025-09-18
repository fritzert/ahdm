package pe.mef.sitfis.seguridad.adapter.inbound.api.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolResponse;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.ApiErrorResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;

@Tag(name = "RolController", description = "API REST para la gestión de roles.")
public interface RolApi {

  @Operation(
      summary = "Listar todos los roles disponibles",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = RolComboResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados.", content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content)
      }
  )
  ResponseEntity<SuccessResponse<List<RolComboResponse>>> listar();

  @Operation(
      summary = "Generar reporte completo de roles",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = RolPaginadoResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados.", content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<RolPaginadoResponse>>> listarTodoReporte();

  @SuppressWarnings("unused")
  @Operation(
      summary = "Buscar roles de manera paginada con filtros opcionales",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista paginada de datos disponibles.", content = @Content(schema = @Schema(implementation = RolPaginadoResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados con los filtros aplicados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. Verifica los parámetros enviados.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Pagina<RolPaginadoResponse>>> buscarRolPaginado(Integer page,
      Integer size, String nombre);

  @Operation(
      summary = "Obtener detalles de un rol por ID",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna el dato asociado al ID proporcionado.", content = @Content(schema = @Schema(implementation = RolResponse.class))),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. El formato o valor del ID proporcionado es incorrecto.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Dato no encontrado para el ID proporcionado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<RolResponse>> obtenerPorId(Long id);

  @Operation(
      summary = "Crear un nuevo rol",
      responses = {
          @ApiResponse(responseCode = "201", description = "Creación exitosa. El dato fue creado satisfactoriamente.", content = @Content(schema = @Schema(implementation = RolInfoResponse.class))),
          @ApiResponse(responseCode = "400", description = "Petición incorrecta. Los datos proporcionados no son válidos o están incompletos.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "409", description = "Conflicto al crear el recurso. Ya existe un registro con datos similares.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<RolInfoResponse>> crear(CrearRolRequest request);

  @Operation(
      summary = "Actualizar un rol existente",
      responses = {
          @ApiResponse(responseCode = "200", description = "Actualización exitosa. El registro fue modificado correctamente.", content = @Content(schema = @Schema(implementation = RolInfoResponse.class))),
          @ApiResponse(responseCode = "400", description = "Petición incorrecta. Los datos proporcionados no son válidos o están incompletos.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Dato no encontrado para el ID proporcionado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<RolInfoResponse>> actualizar(Long id,
      ActualizarRolRequest request);

  @SuppressWarnings("unused")
  @Operation(
      summary = "Eliminar un rol por ID",
      responses = {
          @ApiResponse(responseCode = "204", description = "Eliminación satisfactoria. El registro fue eliminado correctamente.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. El formato o valor del ID proporcionado es inválido.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Dato no encontrado para el ID proporcionado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Void>> eliminar(Long rolId);

  @SuppressWarnings("unused")
  @Operation(
      summary = "Listar todos los roles disponibles restringidos a un grupo",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = RolComboResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. El formato o valor del ID proporcionado es inválido.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<RolComboResponse>>> listarRestricciones(Long grupoId);

}
