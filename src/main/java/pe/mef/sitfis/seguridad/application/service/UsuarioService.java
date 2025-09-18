package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearUsuarioRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioDisponibleResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioPersonaRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioPersonaResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioResponse;

public interface UsuarioService {

  Page<UsuarioPersonaResponse> findAllPaginacion(Pageable pageable, Integer page, Integer size);

  Page<UsuarioPersonaResponse> findByUsuario(
      String cuenta, int estado, String nombre, Long tipoDocumentoId, String numeroDocumento,
      Pageable pageable);

  UsuarioDisponibleResponse verificarDisponibilidadUsuario(String cuenta);

  boolean crear(CrearUsuarioRequest request);

  boolean update(UUID id, UsuarioPersonaRequest request);

  Optional<UsuarioResponse> findBySoloUsuario(String usuario);

  List<UsuarioComboResponse> findAllUsuarioSinRoles(String usuario);

  List<UsuarioComboResponse> listarTodosUsuarioConRol(String usuario);
  
  UUID obtenerPersonaId(String usuario);

}