package pe.mef.sitfis.seguridad.adapter.outbound.bd;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.SubMenuRepository;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarSubmenuPorMenuIdPort;
import pe.mef.sitfis.seguridad.domain.aggregate.SubMenuAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.SubmenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuNivelValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuNombreValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuOrdenValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuRutaValue;
import pe.mef.sitfis.seguridad.domain.query.ListarSubMenuDomainQuery;

@Component
@RequiredArgsConstructor
public class SubMenuPersistenceAdapter implements ListarSubmenuPorMenuIdPort {

  private final SubMenuRepository repository;

  /*
  @Override
  public List<SubMenuDto> listarTodosPorMenuId(String filtro, Long menuId) {
    return repository.findAllSubMenuByMenuId(filtro, menuId).stream()
        .map(mapper::toDto)
        .toList();
  }
   */

  @Override
  public List<SubMenuAggregate> listarTodosPorMenuId(ListarSubMenuDomainQuery query) {
    var entities = repository.findAllSubMenuByMenuId(query.criterioNombre(),
        query.menuId().valor());

    return entities.stream()
        .map(entity -> SubMenuAggregate.reconstruir(
            SubmenuId.de(entity.getId()),
            MenuId.de(entity.getId()),
            SubMenuNombreValue.de(entity.getNombre()),
            SubMenuNivelValue.de(entity.getNivel()),
            SubMenuRutaValue.de(entity.getRuta()),
            SubMenuOrdenValue.de(entity.getOrden())
        ))
        .toList();
  }


}
