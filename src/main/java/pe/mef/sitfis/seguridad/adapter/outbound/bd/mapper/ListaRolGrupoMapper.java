package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearListaRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoMenuResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ListaRolGrupoReporteResponse;
import pe.mef.sitfis.seguridad.application.command.CrearListaRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoMenuDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoPaginadoDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoReporteDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListaRolGrupoMapper {

  List<ListaRolGrupoReporteResponse> toListReporteResponse(List<ListaRolGrupoReporteDto> dtoList);

  List<ListaRolGrupoPaginadoResponse> toListPaginadoResponse(
      List<ListaRolGrupoPaginadoDto> dtoList);

  List<ListaRolGrupoMenuResponse> toListResponse(List<ListaRolGrupoMenuDto> dtoList);

  List<CrearListaRolGrupoCommand> toListCommand(List<CrearListaRolGrupoRequest> request);

}
