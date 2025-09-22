package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.TabJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.TabMenuSubmenuProjection;
import pe.mef.sitfis.seguridad.application.dto.TabPaginadoDto;
import pe.mef.sitfis.seguridad.domain.aggregate.TabAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.SubmenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.TabId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabComponenteValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabNombreValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.TabOrdenValue;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TabJpaMapper {

  default TabAggregate toAggregate(TabJpaEntity entity) {
    if (entity == null) {
      return null;
    }

    return TabAggregate.reconstruir(
        TabId.de(entity.getId()),
        new TabNombreValue(entity.getNombre()),
        new TabComponenteValue(entity.getComponente()),
        new TabOrdenValue(entity.getOrden()),
        MenuId.de(entity.getMenu().getId()),
        SubmenuId.de(entity.getSubmenu().getId())
    );
  }

  TabPaginadoDto toPaginadoDto(TabMenuSubmenuProjection projection);

}
