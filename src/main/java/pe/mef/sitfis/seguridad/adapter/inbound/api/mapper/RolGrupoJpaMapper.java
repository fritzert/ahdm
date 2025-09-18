package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.GrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.RolGrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.RolJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.RolGrupoProjection;
import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;
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

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolGrupoJpaMapper {

  RolGrupoDto toDto(RolGrupoProjection projection);

  @Mapping(target = "roles", source = "rolId", qualifiedByName = "rolFromId")
  @Mapping(target = "grupos", source = "grupoId", qualifiedByName = "grupoFromId")
  RolGrupoJpaEntity toEntity(CrearRolGrupoCommand request);

  @Named("rolFromId")
  default RolJpaEntity rolFromId(Long id) {
    if (id == null) {
      return null;
    }
    RolJpaEntity rol = new RolJpaEntity();
    rol.setId(id);
    return rol;
  }

  @Named("grupoFromId")
  default GrupoJpaEntity grupoFromId(Long id) {
    if (id == null) {
      return null;
    }
    GrupoJpaEntity grupo = new GrupoJpaEntity();
    grupo.setId(id);
    return grupo;
  }


  default RolGrupoAggregate toAggregate(RolGrupoJpaEntity entity) {
    if (entity == null) {
      return null;
    }

    return new RolGrupoAggregate(
        RolGrupoId.de(entity.getId()),
        RolId.de(entity.getRoles().getId()),
        GrupoId.de(entity.getGrupos().getId()),
        new RolGrupoFlagRestriccionValue(entity.getFlagRestriccion()),
        new RolGrupoFlagConsultaValue(entity.getFlagConsulta()),
        new RolGrupoFlagOperacionValue(entity.getFlagOperacion()),
        new RolGrupoFlagAsignarRecursosValue(entity.getFlagAsignarRecursos()),
        new RolGrupoFlagEnviarBandejaValue(entity.getFlagEnviarBandeja()),
        new RolGrupoFlagEnviarEtapaValue(entity.getFlagEnviarEtapa()),
        new RolGrupoFlagAdjuntarArchivoValue(entity.getFlagAdjuntarArchivo())
    );
  }

  @Mapping(target = "rolId", source = "roles.id")
  @Mapping(target = "grupoId", source = "grupos.id")
  RolGrupoInfoDto toInfoDto(RolGrupoJpaEntity resultado);

}
