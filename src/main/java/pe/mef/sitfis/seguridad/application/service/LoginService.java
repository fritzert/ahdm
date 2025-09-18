package pe.mef.sitfis.seguridad.application.service;

import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoMenuResponse;

public interface LoginService {

  UsuarioRolGrupoMenuResponse obtenerToken(String cuenta, String clave);

  Integer logout();
}
