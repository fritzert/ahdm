package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.RolJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.RolComboProjection;
import pe.mef.sitfis.seguridad.application.dto.CrearRolDto;
import pe.mef.sitfis.seguridad.application.dto.RolComboDto;
import pe.mef.sitfis.seguridad.application.dto.RolDto;
import pe.mef.sitfis.seguridad.application.dto.RolInfoDto;
import pe.mef.sitfis.seguridad.application.dto.RolPaginadoDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolJpaMapper {

  RolComboDto toComboDto(RolComboProjection projection);

  RolPaginadoDto toPaginadoDto(RolJpaEntity entity);

  RolDto toDto(RolJpaEntity entity);

  RolJpaEntity toEntity(CrearRolDto dto);

  RolInfoDto toInfoDto(RolJpaEntity entity);

}
