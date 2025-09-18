package pe.mef.sitfis.seguridad.adapter.inbound.api.spec;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import org.springframework.http.ResponseEntity;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.MenuFiltroResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.MenuResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.ApiErrorResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;

@Tag(name = "MenuController", description = "API REST para la gestión de menus.")
public interface MenuApi {

  @SuppressWarnings("unused")
  @Operation(
      summary = "Buscar menus por nombre y filtros opcionales",
      responses = {
          @ApiResponse(responseCode = "200", description = "Operación exitosa. Retorna una lista de datos disponibles.", content = @Content(schema = @Schema(implementation = MenuResponse.class))),
          @ApiResponse(responseCode = "204", description = "Solicitud exitosa, pero no se encontraron resultados con los filtros aplicados.", content = @Content),
          @ApiResponse(responseCode = "400", description = "Solicitud inválida. Verifica los parámetros enviados.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
          @ApiResponse(responseCode = "500", description = "Error interno del servidor. No se pudo procesar la solicitud.", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
      }
  )
  ResponseEntity<SuccessResponse<List<MenuFiltroResponse>>> listarTodosMenu(String nombre);

}
