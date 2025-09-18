package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.MenuDto;
import pe.mef.sitfis.seguridad.application.query.ListarMenuApplicationQuery;

public interface ListarMenuUseCase {

  /**
   * Lista todos los menus aplicando los criterios especificados.
   *
   * @param query consulta de aplicacion con parametros de filtrado
   * @return lista de agregados de menu
   */
  List<MenuDto> listarTodosMenu(ListarMenuApplicationQuery query);

}
