package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.SubMenuResponse;
import pe.mef.sitfis.seguridad.application.dto.SubMenuDto;
import pe.mef.sitfis.seguridad.application.query.ListarSubMenuApplicationQuery;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubMenuApiMapper {

  ListarSubMenuApplicationQuery toQuery(String nombre, Long menuId);

  List<SubMenuResponse> toResponse(List<SubMenuDto> dtoList);

}
