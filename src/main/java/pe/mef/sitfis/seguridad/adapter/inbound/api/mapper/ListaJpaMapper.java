package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.ListaJpaEntity;
import pe.mef.sitfis.seguridad.application.dto.ListaDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListaJpaMapper {

  @Mapping(source = "tabId", target = "tab.id")
  @Mapping(source = "submenuId", target = "submenu.id")
  @Mapping(source = "menuId", target = "menu.id")
  ListaJpaEntity toEntity(ListaDto dto);

}
