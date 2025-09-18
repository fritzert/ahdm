package pe.mef.sitfis.seguridad.adapter.outbound.bd;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper.TabJpaMapper;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.TabRepository;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarTabPorMenuSubmenuPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarTabPorParametrosPaginadoPort;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.domain.aggregate.TabAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.SubmenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.TabId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabComponenteValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabNombreValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabOrdenValue;
import pe.mef.sitfis.seguridad.domain.query.BuscarTabDomainQuery;
import pe.mef.sitfis.seguridad.domain.query.BuscarTabPaginadoDomainQuery;
//import pe.mef.sitfis.seguridad.domain.query.BuscarTabDomainQuery;
//import pe.mef.sitfis.seguridad.domain.query.BuscarTabPaginadoDomainQuery;
//import pe.mef.sitfis.seguridad.domain.query.PaginaDomainQuery;

@Slf4j
@Component
@RequiredArgsConstructor
public class TabPersistenceAdapter implements
    BuscarTabPorMenuSubmenuPort,
    BuscarTabPorParametrosPaginadoPort {

  private final TabRepository repository;
  private final TabJpaMapper mapper;

  @Override
  public List<TabAggregate> buscarTabs(BuscarTabDomainQuery query) {
    var entities = repository.findAllByNombreMenuSubmenu(query.nombre(),
        query.submenuId().valor(),
        query.menuId().valor()
    );

    return entities.stream()
        .map(entity -> TabAggregate.reconstruir(
            TabId.de(entity.getId()),
            TabNombreValue.de(entity.getNombre()),
            TabComponenteValue.de(entity.getComponente()),
            TabOrdenValue.de(entity.getOrden()),
            MenuId.de(entity.getMenu().getId()),
            SubmenuId.de(entity.getSubmenu().getId())
        ))
        .toList();
  }

  

  /*
  @Override
  public Pagina<TabPaginadoDto> buscarPaginado(BuscarTabPaginadoApplicationQuery query) {
    Long menuId = query.menuId();
    Long submenuId = query.submenuId();
    Long tabId = query.tabId();
//    PaginaDomainQuery paginaQuery = query.paginaDomainQuery();
    PaginaApplicationQuery paginaQuery = query.pagina();

    var pageable = PageRequest.of(paginaQuery.pagina(), paginaQuery.tamanio(),
        Sort.by(Sort.Direction.DESC, "tabNombre"));

    var resultado = repository.findByMenuSubmenuTabPaginado(
        menuId, submenuId, tabId, pageable);

    var dtos = resultado.getContent().stream()
        .map(mapper::toPaginadoDto)
        .toList();

    return new Pagina<>(
        dtos,
        resultado.getNumber(),
        resultado.getTotalPages(),
        resultado.getTotalElements()
    );
  }
   */
}
