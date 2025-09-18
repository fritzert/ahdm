package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.SubmenuJpaEntity;
import pe.mef.sitfis.seguridad.application.dto.SubMenuDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubmenuJpaMapper {

  SubMenuDto toDto(SubmenuJpaEntity entity);

}
