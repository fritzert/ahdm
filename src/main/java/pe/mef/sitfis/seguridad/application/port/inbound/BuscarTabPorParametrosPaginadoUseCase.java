package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.dto.TabPaginadoDto;
import pe.mef.sitfis.seguridad.application.query.BuscarTabPaginadoApplicationQuery;
import pe.mef.sitfis.seguridad.application.query.Pagina;

public interface BuscarTabPorParametrosPaginadoUseCase {

//  Pagina<TabPaginadoDto> buscarPorMenuSubmenuTabPaginado(Long menuId, Long submenuId,
//      Long tabId, PaginaApplicationQuery query);

  Pagina<TabPaginadoDto> buscarPorMenuSubmenuTabPaginado(BuscarTabPaginadoApplicationQuery query);

}
