package pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.SubMenuResponse;
import pe.mef.sitfis.seguridad.application.dto.SubMenuDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubMenuMapper {

  List<SubMenuResponse> toResponse(List<SubMenuDto> dtoList);

}
