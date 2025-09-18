package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoDTO;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.RolGrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioRolGrupoEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioRolGrupoMapper {

  @Mapping(source = "usuario.id", target = "id")
  @Mapping(source = "usuario.cuenta", target = "usuarioNombre")
  @Mapping(source = "rolGrupo.id", target = "rolGrupoId")
  @Mapping(source = "rolGrupo.roles.nombre", target = "rolNombre")
  @Mapping(source = "rolGrupo.grupos.nombre", target = "grupoNombre")
  UsuarioRolGrupoDTO toDTO(UsuarioRolGrupoEntity entity);

  @Mapping(target = "usuario.id", source = "id") // viene del id del usuario
  @Mapping(target = "rolGrupo.id", source = "rolGrupoId")
  UsuarioRolGrupoEntity toEntity(UsuarioRolGrupoDTO dto);

  // crearActualizarUsuarioRolGrupo -------------------------------------------------------------------

  @Mapping(target = "usuario", source = "usuarioId")
  @Mapping(target = "rolGrupo", source = "rolGrupoId")
  UsuarioRolGrupoEntity toEntity(UsuarioRolGrupoRequest request);

  List<UsuarioRolGrupoEntity> toEntityList(List<UsuarioRolGrupoRequest> requestList);

  // Sub-mapeos
  default UsuarioEntity toListaEntity(UsuarioRolGrupoRequest.UsuarioRecord record) {
      if (record == null) {
          return null;
      }
    UsuarioEntity entity = new UsuarioEntity();
    entity.setId(record.id());
    return entity;
  }

  default RolGrupoJpaEntity toRolGrupoEntity(UsuarioRolGrupoRequest.RolGrupoRecord record) {
      if (record == null) {
          return null;
      }
    RolGrupoJpaEntity entity = new RolGrupoJpaEntity();
    entity.setId(record.id());
    return entity;
  }
}
