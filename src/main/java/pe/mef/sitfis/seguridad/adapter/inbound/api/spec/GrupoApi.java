package pe.mef.sitfis.seguridad.adapter.inbound.api.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoResponse;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.ApiErrorResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;

@Tag(name = "GrupoController", description = "API REST para la gestión de grupos.")
public interface GrupoApi {

  @Operation(
      summary = "Listar todos los grupos disponibles",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = GrupoComboResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados.", content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<GrupoComboResponse>>> listar();

  @Operation(
      summary = "Generar reporte completo de grupos",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = GrupoPaginadoResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados.", content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<GrupoPaginadoResponse>>> listarTodoReporte();

  @SuppressWarnings("unused")
  @Operation(
      summary = "Buscar grupos de manera paginada con filtros opcionales",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista paginada de datos disponibles.", content = @Content(schema = @Schema(implementation = GrupoPaginadoResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados con los filtros aplicados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. Verifica los parámetros enviados.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Pagina<GrupoPaginadoResponse>>> buscarGrupoPaginado(
      Integer page, Integer size, String nombre, Long grupoId, Long rolId);

  @Operation(
      summary = "Obtener detalles de un grupo por ID",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna el dato asociado al ID proporcionado.", content = @Content(schema = @Schema(implementation = GrupoResponse.class))),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. El formato o valor del ID proporcionado es incorrecto.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Dato no encontrado para el ID proporcionado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<GrupoResponse>> obtenerPorId(Long grupoId);

  @Operation(
      summary = "Crear un nuevo grupo",
      responses = {
          @ApiResponse(responseCode = "201", description = "Creación exitosa. El dato fue creado satisfactoriamente.", content = @Content(schema = @Schema(implementation = GrupoInfoResponse.class))),
          @ApiResponse(responseCode = "400", description = "Petición incorrecta. Los datos proporcionados no son válidos o están incompletos.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "409", description = "Conflicto al crear el recurso. Ya existe un registro con datos similares.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<GrupoInfoResponse>> crear(CrearGrupoRequest request);

  @Operation(
      summary = "Actualizar un grupo existente",
      responses = {
          @ApiResponse(responseCode = "200", description = "Actualización exitosa. El registro fue modificado correctamente.", content = @Content(schema = @Schema(implementation = GrupoInfoResponse.class))),
          @ApiResponse(responseCode = "400", description = "Petición incorrecta. Los datos proporcionados no son válidos o están incompletos.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Dato no encontrado para el ID proporcionado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<GrupoInfoResponse>> actualizar(Long grupoId,
      ActualizarGrupoRequest request);

  @Operation(
      summary = "Eliminar un grupo por ID",
      responses = {
          @ApiResponse(responseCode = "204", description = "Eliminación satisfactoria. El registro fue eliminado correctamente.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. El formato o valor del ID proporcionado es inválido.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Dato no encontrado para el ID proporcionado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Void>> eliminar(Long grupoId);

}