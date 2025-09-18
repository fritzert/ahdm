package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolResponse;
import pe.mef.sitfis.seguridad.application.command.ActualizarRolCommand;
import pe.mef.sitfis.seguridad.application.command.CrearRolCommand;
import pe.mef.sitfis.seguridad.application.dto.RolComboDto;
import pe.mef.sitfis.seguridad.application.dto.RolDto;
import pe.mef.sitfis.seguridad.application.dto.RolInfoDto;
import pe.mef.sitfis.seguridad.application.dto.RolPaginadoDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RolMapper {

  RolComboResponse toComboResponse(RolComboDto dto);

  RolPaginadoResponse toPaginadoResponse(RolPaginadoDto dto);

  List<RolPaginadoResponse> toListPaginadoResponse(List<RolPaginadoDto> dtoList);

  RolResponse toResponse(RolDto dto);

  CrearRolCommand toCommand(CrearRolRequest request);

  RolInfoResponse toInfoResponse(RolInfoDto dto);

  ActualizarRolCommand toActualizarCommand(ActualizarRolRequest request);

}
