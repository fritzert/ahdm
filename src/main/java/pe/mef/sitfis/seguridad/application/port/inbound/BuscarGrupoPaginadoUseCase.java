package pe.mef.sitfis.seguridad.application.port.inbound;

import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.dto.GrupoPaginadoDto;

public interface BuscarGrupoPaginadoUseCase {

  Pagina<GrupoPaginadoDto> buscarGrupoPaginado(String nombre, Long grupoId, Long rolId,
      PaginaApplicationQuery query);
}
