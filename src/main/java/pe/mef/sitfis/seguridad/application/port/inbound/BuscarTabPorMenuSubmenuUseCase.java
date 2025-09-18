package pe.mef.sitfis.seguridad.application.port.inbound;

import java.util.List;
import pe.mef.sitfis.seguridad.application.dto.TabDto;
import pe.mef.sitfis.seguridad.application.query.BuscarTabApplicationQuery;

public interface BuscarTabPorMenuSubmenuUseCase {

  List<TabDto> buscarPorMenuSubmenu(BuscarTabApplicationQuery query);

}
