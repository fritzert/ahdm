package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.GrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.GrupoComboProjection;
import pe.mef.sitfis.seguridad.application.dto.CrearGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoComboDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoInfoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoPaginadoDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GrupoJpaMapper {

  GrupoComboDto toComboDto(GrupoComboProjection projection);

  GrupoPaginadoDto toPaginadoDto(GrupoJpaEntity entity);

  GrupoDto toDto(GrupoJpaEntity entity);

  GrupoJpaEntity toEntity(CrearGrupoDto dto);

  GrupoInfoDto toInfoDto(GrupoJpaEntity entity);

}
