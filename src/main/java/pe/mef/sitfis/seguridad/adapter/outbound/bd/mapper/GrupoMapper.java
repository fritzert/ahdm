package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoInfoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoResponse;
import pe.mef.sitfis.seguridad.application.command.ActualizarGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.CrearGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.GrupoComboDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoInfoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoPaginadoDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface GrupoMapper {

  GrupoComboResponse toComboResponse(GrupoComboDto dto);

  GrupoPaginadoResponse toPaginadoResponse(GrupoPaginadoDto dto);

  List<GrupoPaginadoResponse> toListPaginadoResponse(List<GrupoPaginadoDto> dtoList);

  GrupoResponse toResponse(GrupoDto dto);

  CrearGrupoCommand toCommand(CrearGrupoRequest request);

  GrupoInfoResponse toInfoResponse(GrupoInfoDto dto);

  ActualizarGrupoCommand toActualizarCommand(ActualizarGrupoRequest request);

}
