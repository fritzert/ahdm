package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.MenuJpaEntity;
import pe.mef.sitfis.seguridad.domain.aggregate.MenuAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.MenuNombreValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.MenuOrdenValue;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuJpaMapper {

  default MenuAggregate toAggregate(MenuJpaEntity entity) {
    if (entity == null) {
      return null;
    }

    return MenuAggregate.reconstruir(
        MenuId.de(entity.getId()),
        new MenuNombreValue(entity.getNombre()),
        new MenuOrdenValue(entity.getOrden())
    );
  }

}
