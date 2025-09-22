package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolGrupoInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolGrupoResponse;
import pe.mef.sitfis.seguridad.application.command.ActualizarRolGrupoApplicationCommand;
import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoApplicationCommand;
import pe.mef.sitfis.seguridad.application.command.EliminarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;
import pe.mef.sitfis.seguridad.application.query.ListarRolGrupoApplicationQuery;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolGrupoApiMapper {

  ListarRolGrupoApplicationQuery toQuery(Long id);

  List<RolGrupoResponse> toListResponse(List<RolGrupoDto> dto);

  @Mapping(target = "rolId", source = "rolId.id")
  @Mapping(target = "grupoId", source = "grupoId.id")
  CrearRolGrupoApplicationCommand toCommand(CrearRolGrupoRequest request);

  RolGrupoInfoResponse toInfoResponse(RolGrupoInfoDto dto);

  @Mapping(target = "rolId", source = "rolId.id")
  @Mapping(target = "grupoId", source = "grupoId.id")
  ActualizarRolGrupoApplicationCommand toCommand(ActualizarRolGrupoRequest request);

  EliminarRolGrupoCommand toCommand(Long id);

//  List<MenuFiltroResponse> toListResponse(List<MenuDto> dtos);

}
