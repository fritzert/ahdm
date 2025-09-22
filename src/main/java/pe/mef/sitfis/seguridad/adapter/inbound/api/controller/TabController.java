package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.TAB_ENDPOINT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.TabComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.TabPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.TabApiMapper;
import pe.mef.sitfis.seguridad.adapter.inbound.api.spec.TabApi;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarTabPorMenuSubmenuUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarTabPorParametrosPaginadoUseCase;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;

@Slf4j
@RestController
@RequestMapping(TAB_ENDPOINT)
@RequiredArgsConstructor
public class TabController implements TabApi {

  private final BuscarTabPorMenuSubmenuUseCase buscarPorMenuSubmenuUseCase;
  private final BuscarTabPorParametrosPaginadoUseCase paginadoUseCase;
  private final TabApiMapper mapper;

  @Override
  @GetMapping
  public ResponseEntity<SuccessResponse<List<TabComboResponse>>> buscarPorMenuSubmenu(
      @RequestParam(value = "nombre", required = false) String nombre,
      @RequestParam(value = "id_submenu") Long submenuId,
      @RequestParam(value = "id_menu") Long menuId) {
    var query = mapper.toQuery(nombre, submenuId, menuId);
    var resultado = buscarPorMenuSubmenuUseCase.buscarPorMenuSubmenu(query);
    var response = mapper.toListResponse(resultado);
    return SuccessResponseHandler.SUCCESS(response);
  }

  @Override
  @GetMapping("/paginado")
  public ResponseEntity<SuccessResponse<Pagina<TabPaginadoResponse>>> buscarPorTabMenuSubmenu(
      @RequestParam(name = "page", defaultValue = "0") Integer page,
      @RequestParam(name = "size", defaultValue = "10") Integer size,
      @RequestParam(value = "id_menu", required = false) Long menuId,
      @RequestParam(value = "id_submenu", required = false) Long submenuId,
      @RequestParam(name = "id_tab", required = false) Long tabId) {
    var query = mapper.toPaginadoQuery(menuId, submenuId, tabId,
        new PaginaApplicationQuery(page, size, null, null));
    var resultado = paginadoUseCase.buscarPorMenuSubmenuTabPaginado(query);
    var response = mapper.toPaginadoResponse(resultado);
    return SuccessResponseHandler.SUCCESS(response);
  }

}
