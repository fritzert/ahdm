package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import pe.mef.sitfis.seguridad.application.dto.TabDto;
import pe.mef.sitfis.seguridad.application.dto.TabPaginadoDto;
import pe.mef.sitfis.seguridad.application.mapper.TabDomainMapper;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarTabPorMenuSubmenuUseCase;
import pe.mef.sitfis.seguridad.application.port.inbound.BuscarTabPorParametrosPaginadoUseCase;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarTabPorMenuSubmenuPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarTabPorParametrosPaginadoPort;
import pe.mef.sitfis.seguridad.application.query.BuscarTabApplicationQuery;
import pe.mef.sitfis.seguridad.application.query.BuscarTabPaginadoApplicationQuery;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.domain.aggregate.TabAggregate;

@RequiredArgsConstructor
public class TabService implements
    BuscarTabPorMenuSubmenuUseCase,
    BuscarTabPorParametrosPaginadoUseCase {

  private final BuscarTabPorMenuSubmenuPort buscarTabPorMenuSubmenuPort;
  private final BuscarTabPorParametrosPaginadoPort paginadoPort;
  private final TabDomainMapper domainMapper;

  @Override
  public List<TabDto> buscarPorMenuSubmenu(BuscarTabApplicationQuery query) {
    var domainQuery = domainMapper.toDomainQuery(query);

    List<TabAggregate> aggregates = buscarTabPorMenuSubmenuPort.buscarTabs(domainQuery);

    return aggregates.stream()
        .map(aggregate -> new TabDto(
            aggregate.getId() != null ? aggregate.getId().valor() : null,
            aggregate.getNombre().valorFormateado(),
            aggregate.getComponente().valorFormateado(),
            aggregate.getOrden().valor()
        ))
        .toList();
  }

  @Override
  public Pagina<TabPaginadoDto> buscarPorMenuSubmenuTabPaginado(
      BuscarTabPaginadoApplicationQuery query) {
    var domainQuery = domainMapper.toPaginadoDomainQuery(query);

    List<TabAggregate> aggregates = paginadoPort.buscarPaginado(domainQuery);

    var applicationQuery = new BuscarTabPaginadoApplicationQuery(menuId, submenuId, tabId,
        paginaQuery);
    return paginadoPort.buscarPaginado(applicationQuery);
  }

  /*
  @Override
  public Pagina<TabPaginadoDto> buscarPorMenuSubmenuTabPaginado(Long menuId, Long submenuId,
      Long tabId, PaginaApplicationQuery paginaQuery) {
    var applicationQuery = new BuscarTabPaginadoApplicationQuery(menuId, submenuId, tabId,
        paginaQuery);
    return paginadoPort.buscarPaginado(applicationQuery);
  }
  */

}
