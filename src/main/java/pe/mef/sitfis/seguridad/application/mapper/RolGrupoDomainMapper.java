package pe.mef.sitfis.seguridad.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pe.mef.sitfis.seguridad.application.query.ListarRolGrupoApplicationQuery;
import pe.mef.sitfis.seguridad.domain.query.ListarRolGrupoDomainQuery;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface RolGrupoDomainMapper {

  RolGrupoDomainMapper INSTANCE = Mappers.getMapper(RolGrupoDomainMapper.class);

  ListarRolGrupoDomainQuery toDomainQuery(ListarRolGrupoApplicationQuery applicationQuery);

}