package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import pe.mef.sitfis.seguridad.application.dto.SubMenuDto;
import pe.mef.sitfis.seguridad.application.mapper.SubMenuDomainMapper;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarSubmenuPorMenuIdUseCase;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarSubmenuPorMenuIdPort;
import pe.mef.sitfis.seguridad.application.query.ListarSubMenuApplicationQuery;
import pe.mef.sitfis.seguridad.domain.aggregate.SubMenuAggregate;

@RequiredArgsConstructor
public class SubMenuService implements ListarSubmenuPorMenuIdUseCase {

  private final ListarSubmenuPorMenuIdPort listarSubmenuPorMenuIdPort;
  private final SubMenuDomainMapper domainMapper;

  @Override
  public List<SubMenuDto> listarTodosPorMenuId(ListarSubMenuApplicationQuery query) {
    var domainQuery = domainMapper.toDomainQuery(query);

    List<SubMenuAggregate> aggregates = listarSubmenuPorMenuIdPort.listarTodosPorMenuId(
        domainQuery);

    return aggregates.stream()
        .map(aggregate -> new SubMenuDto(
            aggregate.getMenuId().valor(),
            aggregate.getId().valor(),
            aggregate.getNombre().valorFormateado(),
            aggregate.getRuta().valorFormateado(),
            aggregate.getNivel().valor(),
            aggregate.getOrden().valor()
        ))
        .toList();
  }

}
