package pe.mef.sitfis.seguridad.adapter.inbound.api.controller;

import static pe.mef.sitfis.seguridad.adapter.inbound.api.util.constantes.PathUtil.SUBMENU_ENDPOINT;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.SubMenuResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.SubMenuApiMapper;
import pe.mef.sitfis.seguridad.adapter.inbound.api.spec.SubmenuApi;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.util.response.SuccessResponseHandler;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarSubmenuPorMenuIdUseCase;

@Slf4j
@RestController
@RequestMapping(SUBMENU_ENDPOINT)
@RequiredArgsConstructor
public class SubMenuController implements SubmenuApi {

  private final ListarSubmenuPorMenuIdUseCase submenuPorMenuIdUseCase;
  private final SubMenuApiMapper mapper;

  @Override
  @GetMapping
  public ResponseEntity<SuccessResponse<List<SubMenuResponse>>> listarTodosPorMenuId(
      @RequestParam(value = "nombre", required = false) String nombre,
      @RequestParam(value = "id_menu") Long menuId) {
    var query = mapper.toQuery(nombre, menuId);
    var resultado = submenuPorMenuIdUseCase.listarTodosPorMenuId(query);
    var response = mapper.toResponse(resultado);
    return SuccessResponseHandler.SUCCESS(response);
  }

}
