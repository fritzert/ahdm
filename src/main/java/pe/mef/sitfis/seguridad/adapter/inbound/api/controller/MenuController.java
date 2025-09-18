package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.MENU_ENDPOINT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.MenuFiltroResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.MenuApiMapper;
import pe.mef.sitfis.seguridad.adapter.inbound.api.spec.MenuApi;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarMenuUseCase;

@Slf4j
@RestController
@RequestMapping(MENU_ENDPOINT)
@RequiredArgsConstructor
public class MenuController implements MenuApi {

  private final ListarMenuUseCase listarMenuUseCase;
  private final MenuApiMapper mapper;

  /**
   * Lista todos los menús con filtro opcional por nombre.
   *
   * @param nombre criterio de filtrado por nombre (opcional)
   * @return respuesta exitosa con lista de menús
   */
  @Override
  @GetMapping
  public ResponseEntity<SuccessResponse<List<MenuFiltroResponse>>> listarTodosMenu(
      @RequestParam(value = "nombre", required = false) String nombre) {
    var query = mapper.toQuery(nombre);
    var resultado = listarMenuUseCase.listarTodosMenu(query);
    var response = mapper.toListResponse(resultado);
    return SuccessResponseHandler.SUCCESS(response);
  }

}
