package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.RolGrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.RolGrupoProjection;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.domain.aggregate.RolGrupoAggregate;
import pe.mef.sitfis.seguridad.domain.aggregate.id.GrupoId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolGrupoId;
import pe.mef.sitfis.seguridad.domain.aggregate.id.RolId;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAdjuntarArchivoValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagAsignarRecursosValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagConsultaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarBandejaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagEnviarEtapaValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagOperacionValue;
import pe.mef.sitfis.seguridad.domain.aggregate.value.RolGrupoFlagRestriccionValue;
import pe.mef.sitfis.seguridad.domain.command.CrearRolGrupoDomainCommand;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolGrupoJpaMapper {

  RolGrupoDto toDto(RolGrupoProjection projection);

  @Mapping(target = "roles.id", source = "rolId.valor")
  @Mapping(target = "grupos.id", source = "grupoId.valor")
  @Mapping(target = "flagRestriccion", source = "flagRestriccion.valor")
  @Mapping(target = "flagConsulta", source = "flagConsulta.valor")
  @Mapping(target = "flagOperacion", source = "flagOperacion.valor")
  @Mapping(target = "flagAsignarRecursos", source = "flagAsignarRecursos.valor")
  @Mapping(target = "flagEnviarBandeja", source = "flagEnviarBandeja.valor")
  @Mapping(target = "flagEnviarEtapa", source = "flagEnviarEtapa.valor")
  @Mapping(target = "flagAdjuntarArchivo", source = "flagAdjuntarArchivo.valor")
  RolGrupoJpaEntity toEntity(CrearRolGrupoDomainCommand request);

  default RolGrupoAggregate toAggregate(RolGrupoJpaEntity entity) {
    if (entity == null) {
      return null;
    }
    return RolGrupoAggregate.reconstruir(
        RolGrupoId.de(entity.getId()),
        RolId.de(entity.getRoles().getId()),
        GrupoId.de(entity.getGrupos().getId()),
        new RolGrupoFlagRestriccionValue(entity.getFlagRestriccion()),
        new RolGrupoFlagConsultaValue(entity.getFlagConsulta()),
        new RolGrupoFlagOperacionValue(entity.getFlagOperacion()),
        new RolGrupoFlagAsignarRecursosValue(entity.getFlagAsignarRecursos()),
        new RolGrupoFlagEnviarBandejaValue(entity.getFlagEnviarBandeja()),
        new RolGrupoFlagEnviarEtapaValue(entity.getFlagEnviarEtapa()),
        new RolGrupoFlagAdjuntarArchivoValue(entity.getFlagAdjuntarArchivo()));
  }

}
