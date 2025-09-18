package pe.mef.sitfis.seguridad.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pe.mef.sitfis.seguridad.application.query.BuscarTabApplicationQuery;
import pe.mef.sitfis.seguridad.application.query.BuscarTabPaginadoApplicationQuery;
import pe.mef.sitfis.seguridad.domain.query.BuscarTabDomainQuery;
import pe.mef.sitfis.seguridad.domain.query.BuscarTabPaginadoDomainQuery;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TabDomainMapper {

  TabDomainMapper INSTANCE = Mappers.getMapper(TabDomainMapper.class);

  @Mapping(target = "menuId.valor", source = "menuId")
  @Mapping(target = "submenuId.valor", source = "submenuId")
  BuscarTabDomainQuery toDomainQuery(BuscarTabApplicationQuery applicationQuery);

  @Mapping(target = "menuId.valor", source = "menuId")
  @Mapping(target = "submenuId.valor", source = "submenuId")
  @Mapping(target = "tabId.valor", source = "tabId")
  BuscarTabPaginadoDomainQuery toPaginadoDomainQuery(BuscarTabPaginadoApplicationQuery query);

}