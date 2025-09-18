package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.ListaRolGrupoMenuProjection;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.ListaRolGrupoProjection;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoMenuDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoPaginadoDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoReporteDto;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ListaRolGrupoJpaMapper {

  ListaRolGrupoReporteDto toReporteDto(ListaRolGrupoProjection projection);

  ListaRolGrupoPaginadoDto toPaginadoDto(ListaRolGrupoProjection projection);

  @Mapping(source = "listaId", target = "lista.listaId")
  @Mapping(source = "menuId", target = "lista.menuId")
  @Mapping(source = "menuNombre", target = "lista.menuNombre")
  @Mapping(source = "submenuId", target = "lista.submenuId")
  @Mapping(source = "submenuNombre", target = "lista.submenuNombre")
  @Mapping(source = "tabId", target = "lista.tabId")
  @Mapping(source = "tabNombre", target = "lista.tabNombre")
  ListaRolGrupoMenuDto toGrupoMenuDto(ListaRolGrupoMenuProjection projection);

}
