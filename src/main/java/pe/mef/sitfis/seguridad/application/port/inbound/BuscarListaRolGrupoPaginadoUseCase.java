package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoPaginadoDto;

public interface BuscarListaRolGrupoPaginadoUseCase {

  Pagina<ListaRolGrupoPaginadoDto> buscarPaginado(Long grupoId, Long rolId,
      PaginaApplicationQuery query);
}

