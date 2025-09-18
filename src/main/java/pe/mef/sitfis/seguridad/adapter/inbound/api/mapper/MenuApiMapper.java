package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.MenuFiltroResponse;
import pe.mef.sitfis.seguridad.application.dto.MenuDto;
import pe.mef.sitfis.seguridad.application.query.ListarMenuApplicationQuery;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MenuApiMapper {

  ListarMenuApplicationQuery toQuery(String nombre);

  List<MenuFiltroResponse> toListResponse(List<MenuDto> dtos);

}
