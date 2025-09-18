package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.TabJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.TabMenuSubmenuProjection;
import pe.mef.sitfis.seguridad.application.dto.TabDto;
import pe.mef.sitfis.seguridad.application.dto.TabPaginadoDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TabJpaMapper {

  TabPaginadoDto toPaginadoDto(TabMenuSubmenuProjection projection);

  TabDto toDto(TabJpaEntity tabJpaEntity);

}
