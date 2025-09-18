package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.dto.TabPaginadoDto;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.domain.query.BuscarTabPaginadoDomainQuery;

public interface BuscarTabPorParametrosPaginadoPort {

  /**
   * Lista todos los tabs paginado aplicando el filtro especificado.
   *
   * @param query consulta de dominio con criterios de filtrado
   * @return lista de agregados de tab que cumplen los criterios
   */
  Pagina<TabPaginadoDto> buscarPaginado(BuscarTabPaginadoDomainQuery query);

}
