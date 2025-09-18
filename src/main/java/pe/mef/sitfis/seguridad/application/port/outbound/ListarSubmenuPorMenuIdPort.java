package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.domain.aggregate.SubMenuAggregate;
import pe.mef.sitfis.seguridad.domain.query.ListarSubMenuDomainQuery;

public interface ListarSubmenuPorMenuIdPort {

  List<SubMenuAggregate> listarTodosPorMenuId(ListarSubMenuDomainQuery query);

}
