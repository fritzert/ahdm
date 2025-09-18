package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.MenuJpaEntity;
import pe.mef.sitfis.seguridad.application.dto.MenuDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuJpaMapper {

  MenuDto toDto(MenuJpaEntity menuJpaEntity);

}
