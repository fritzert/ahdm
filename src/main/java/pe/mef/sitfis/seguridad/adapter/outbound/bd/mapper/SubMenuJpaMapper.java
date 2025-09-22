package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.SubmenuJpaEntity;
import pe.mef.sitfis.seguridad.domain.aggregate.SubMenuAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.id.MenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.SubmenuId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuNivelValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuNombreValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuOrdenValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.SubMenuRutaValue;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubMenuJpaMapper {

  default SubMenuAggregate toAggregate(SubmenuJpaEntity entity) {
    if (entity == null) {
      return null;
    }

    return SubMenuAggregate.reconstruir(
        SubmenuId.de(entity.getId()),
        MenuId.de(entity.getId()),
        SubMenuNombreValue.de(entity.getNombre()),
        SubMenuNivelValue.de(entity.getNivel()),
        SubMenuRutaValue.de(entity.getRuta()),
        SubMenuOrdenValue.de(entity.getOrden())
    );
  }

}
