package pe.mef.sitfis.seguridad.application.port.outbound;

import java.util.List;
import pe.mef.sitfis.seguridad.domain.aggregate.TabAggregate;
import pe.mef.sitfis.seguridad.domain.query.BuscarTabDomainQuery;

public interface BuscarTabPorMenuSubmenuPort {

  /**
   * Lista todos los tabs aplicando el filtro especificado.
   *
   * @param query consulta de dominio con criterios de filtrado
   * @return lista de dto de tab que cumplen los criterios
   */
//  List<TabDto> buscarTabs(BuscarTabDomainQuery query);

  List<TabAggregate> buscarTabs(BuscarTabDomainQuery query);

}
