package pe.mef.sitfis.seguridad.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pe.mef.sitfis.seguridad.application.dto.TabDto;
import pe.mef.sitfis.seguridad.application.query.BuscarTabApplicationQuery;
import pe.mef.sitfis.seguridad.application.query.BuscarTabPaginadoApplicationQuery;
import pe.mef.sitfis.seguridad.domain.aggregate.TabAggregate;
import pe.mef.sitfis.seguridad.domain.query.BuscarTabDomainQuery;
import pe.mef.sitfis.seguridad.domain.query.BuscarTabPaginadoDomainQuery;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface TabDomainMapper {

  TabDomainMapper INSTANCE = Mappers.getMapper(TabDomainMapper.class);

  @Mapping(target = "menuId.valor", source = "menuId")
  @Mapping(target = "submenuId.valor", source = "submenuId")
  BuscarTabDomainQuery toDomainQuery(BuscarTabApplicationQuery applicationQuery);

  default TabDto toDto(TabAggregate aggregate) {
    return new TabDto(
        aggregate.getId() != null ? aggregate.getId().valor() : null,
        aggregate.getNombre().valorFormateado(),
        aggregate.getComponente().valorFormateado(),
        aggregate.getOrden().valor());
  }

  default BuscarTabPaginadoDomainQuery toPaginadoDomainQuery(
      BuscarTabPaginadoApplicationQuery query) {
    if (query == null) {
      return null;
    }
    return BuscarTabPaginadoDomainQuery.crear(
        query.menuId(),
        query.submenuId(),
        query.tabId(),
        query.paginaApplicationQuery());
  }

}