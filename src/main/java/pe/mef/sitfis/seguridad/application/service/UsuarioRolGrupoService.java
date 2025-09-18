package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import java.util.UUID;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.EliminarMultipleUsuarioRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioCopiarRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoDTO;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoPrincipalRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoRequest;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.exception.ServiceException;

public interface UsuarioRolGrupoService {

  Pagina<UsuarioRolGrupoPaginadoResponse> buscarUsuarioRolGrupoPaginado(String cuenta,
      Long tipoDocumentoId, String numeroDocumento, PaginaApplicationQuery query);

  List<UsuarioRolGrupoDTO> findByIDUsuario(UUID usuarioId) throws ServiceException;

  boolean crearActualizarUsuarioRolGrupo(List<UsuarioRolGrupoRequest> request);

  boolean actualizarUsuarioRolGrupoPrincipal(UUID usuarioId,
      UsuarioRolGrupoPrincipalRequest request);

  boolean grabarCopiarRoles(UsuarioCopiarRolRequest request);

  void eliminarMultipleUsuarioRolGrupo(EliminarMultipleUsuarioRolGrupoRequest request);
}
