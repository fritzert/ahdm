package pe.mef.sitfis.seguridad.adapter.inbound.api.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.TabComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.TabPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.TabResponse;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.ApiErrorResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;

@Tag(name = "TabController", description = "API REST para la gestión de tabs.")
public interface TabApi {

  @SuppressWarnings("unused")
  @Operation(
      summary = "Buscar tabs por nombre con filtros obligatorios",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = TabResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados con los filtros aplicados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. Verifica los parámetros enviados.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<TabComboResponse>>> buscarPorMenuSubmenu(String nombre,
      Long submenuId, Long menuId);

  @SuppressWarnings("unused")
  @Operation(
      summary = "Buscar tabs de manera paginada con filtros opcionales",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista paginada de datos disponibles.", content = @Content(schema = @Schema(implementation = TabPaginadoResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados con los filtros aplicados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. Verifica los parámetros enviados.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<Pagina<TabPaginadoResponse>>> buscarPorTabMenuSubmenu(Integer page,
      Integer size, Long menuId, Long submenuId, Long tabId);

}
