package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.SubMenuDto;
import pe.mef.sitfis.seguridad.application.query.ListarSubMenuApplicationQuery;

public interface ListarSubmenuPorMenuIdUseCase {

  List<SubMenuDto> listarTodosPorMenuId(ListarSubMenuApplicationQuery query);

}
