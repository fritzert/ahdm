package pe.mef.sitfis.seguridad.application.port.outbound;

import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoPaginadoDto;

public interface ObtenerListaRolGrupoPaginadoPort {

  Pagina<ListaRolGrupoPaginadoDto> obtenerListaRolGrupoPaginado(Long grupoId, Long rolId,
      PaginaApplicationQuery query);

}
