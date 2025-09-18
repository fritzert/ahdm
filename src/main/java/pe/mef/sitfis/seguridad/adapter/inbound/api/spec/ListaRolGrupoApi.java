package pe.mef.sitfis.seguridad.adapter.inbound.api.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearListaRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoMenuResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoReporteResponse;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.ApiErrorResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;

@Tag(name = "ListaRolGrupoController", description = "API REST para la gestión de listaRolGrupo.")
public interface ListaRolGrupoApi {

  @Operation(
      summary = "Generar reporte completo de listaRolGrupo",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = ListaRolGrupoReporteResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados.", content = @Content),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<ListaRolGrupoReporteResponse>>> listarTodoReporte();

  @SuppressWarnings("unused")
  @Operation(
      summary = "Buscar listaRolGrupo de manera paginada con filtros opcionales",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista paginada de datos disponibles.", content = @Content(schema = @Schema(implementation = ListaRolGrupoPaginadoResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados con los filtros aplicados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. Verifica los parámetros enviados.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Pagina<ListaRolGrupoPaginadoResponse>>> listarRolGrupo(
      Integer page, Integer size, Long grupoId, Long rolId);

  @SuppressWarnings("unused")
  @Operation(
      summary = "Buscar listaRolGrupo y sus menus con filtros obligatorios",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista paginada de datos disponibles.", content = @Content(schema = @Schema(implementation = GrupoPaginadoResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados con los filtros aplicados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. Verifica los parámetros enviados.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<ListaRolGrupoMenuResponse>>> listarMenuGrupoRol(
      Long grupoId, Long rolId);

  @Operation(
      summary = "Crear una nueva listaRolGrupo",
      responses = {
          @ApiResponse(responseCode = "201", description = "Creación exitosa. El dato fue creado satisfactoriamente.", content = @Content(schema = @Schema(implementation = Boolean.class))),
          @ApiResponse(responseCode = "400", description = "Petición incorrecta. Los datos proporcionados no son válidos o están incompletos.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "409", description = "Conflicto al crear el recurso. Ya existe un registro con datos similares.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Boolean>> crear(
      @RequestBody @Valid List<CrearListaRolGrupoRequest> request);

  @Operation(
      summary = "Eliminar una listaRolGrupo por ID",
      responses = {
          @ApiResponse(responseCode = "204", description = "Eliminación satisfactoria. El registro fue eliminado correctamente.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. El formato o valor del ID proporcionado es inválido.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "404", description = "Dato no encontrado para el ID proporcionado.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Void>> eliminar(UUID listaRolGrupoId);

}
