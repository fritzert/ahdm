package pe.mef.sitfis.seguridad.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pe.mef.sitfis.seguridad.application.query.ListarSubMenuApplicationQuery;
import pe.mef.sitfis.seguridad.domain.query.ListarSubMenuDomainQuery;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface SubMenuDomainMapper {

  SubMenuDomainMapper INSTANCE = Mappers.getMapper(SubMenuDomainMapper.class);

  @Mapping(target = "criterioNombre", source = "nombre")
  @Mapping(target = "menuId.valor", source = "menuId")
  ListarSubMenuDomainQuery toDomainQuery(ListarSubMenuApplicationQuery applicationQuery);

}