package pe.mef.sitfis.seguridad.application.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarPersonaDocumentoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.ActualizarPersonaRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearUsuarioDto;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearUsuarioRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioDisponibleResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioPersonaRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioPersonaResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.UsuarioMapper;
import pe.mef.sitfis.seguridad.adapter.outbound.auth.client.KeycloakClient;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.UsuarioRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.client.PersonaClient;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.PersonaComboResponse;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.PersonaResponse;
import pe.mef.sitfis.seguridad.application.exception.BusinessException;
import pe.mef.sitfis.seguridad.application.exception.ResourceNotFoundException;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsuarioServiceImpl implements UsuarioService {

  private final UsuarioRepository usuarioRepository;
  private final UsuarioMapper usuarioMapper;
  private final PersonaClient personaClient;
  private final KeycloakClient keycloakClient;

//    @Override
//    public Optional<UsuarioResponse> findById(UUID id) throws ServiceException {
//        Optional<UsuarioEntity> optusuarioEntity = usuarioRepository.findById(id);
//        if (optusuarioEntity.isPresent()) {
//            UsuarioEntity usuarioEntity = optusuarioEntity.get();
//            UsuarioResponse usuarioDTO = usuarioMapper.toDTO(usuarioEntity);
//            return Optional.ofNullable(usuarioDTO);
//        }
//        return Optional.empty();
//    }

  @Override
  public Page<UsuarioPersonaResponse> findAllPaginacion(Pageable pageable, Integer page,
      Integer size) {
    log.info("Obteniendo usuarios paginados desde el repositorio");
    List<UsuarioPersonaResponse> lstUsuarioPersonaResponse = new ArrayList<>();
    PersonaResponse personaResponse = null;

    Page<UsuarioEntity> usuariosPage = usuarioRepository.findAllPaginacion(pageable);

    for (UsuarioEntity usuarioEntity : usuariosPage.getContent()) {

      PersonaResponse optPersonaResponse = personaClient.obtenerPorId(usuarioEntity.getPersonaId());
      if (optPersonaResponse == null) {
        throw new ResourceNotFoundException("Persona no encontrada");
      }
      personaResponse = optPersonaResponse;

      UsuarioPersonaResponse usuarioPersonaResponse = new UsuarioPersonaResponse(
          usuarioEntity.getId(), personaResponse,
          usuarioEntity.getCuenta(), usuarioEntity.getClave(), usuarioEntity.getEstado()
      );

      lstUsuarioPersonaResponse.add(
          usuarioPersonaResponse); // <- ahora sí agregas un usuario diferente cada vez
    }
    return new PageImpl<>(lstUsuarioPersonaResponse, pageable, usuariosPage.getTotalElements());
  }
  
  
  @Override
  public UUID obtenerPersonaId(String usuario) {
	  UUID personaId = usuarioRepository.obtenerPersonaId(usuario);
    if (personaId== null) {
    	throw new ResourceNotFoundException("Persona no encontrada");
    }
    return personaId;
  }

  @Override
  public Optional<UsuarioResponse> findBySoloUsuario(String usuario) {
    Optional<UsuarioEntity> optCursoEntity = usuarioRepository.findByUsuario(usuario);
    if (optCursoEntity.isPresent()) {
      UsuarioEntity usuarioEntity = optCursoEntity.get();
      UsuarioResponse usuarioDTO = usuarioMapper.toDTO(usuarioEntity);
      return Optional.ofNullable(usuarioDTO);
    }
    return Optional.empty();
  }

  @Override
  public Page<UsuarioPersonaResponse> findByUsuario(String cuenta, int estado, String nombre,
      Long tipoDocumentoId, String numeroDocumento, Pageable pageable) {
    List<UsuarioPersonaResponse> lstUsuarioDTO = new ArrayList<>();
    List<PersonaResponse> lstPersonaResponse;
    Page<UsuarioEntity> usuariosPage = Page.empty();

    if (nombre == null && tipoDocumentoId.toString().trim().equals("-1") &&
        (numeroDocumento == null || numeroDocumento.trim().isEmpty())
        ) {
      cuenta = (cuenta != null) ? "%" + cuenta.trim().toUpperCase() + "%" : null;
      usuariosPage = usuarioRepository.findbyCuentaFiltro(cuenta, estado, pageable);

      var lstPersonIds = usuariosPage.getContent().stream()
          .map(UsuarioEntity::getPersonaId)
          .filter(Objects::nonNull)
          .distinct()
          .toList();

      lstPersonaResponse = personaClient.obtenerPersonasPorIds(lstPersonIds);
      lstUsuarioDTO = armarUsuario(usuariosPage, lstPersonaResponse);
    } else {
      cuenta = (cuenta != null) ? "%" + cuenta.trim().toUpperCase() + "%" : null;
      lstPersonaResponse = personaClient.buscarPersonasPorParametros(nombre, tipoDocumentoId,
    		  numeroDocumento);
      var personIds = lstPersonaResponse.stream()
          .map(PersonaResponse::id) // ← este getIddevuelve UUID con guiones
          .filter(Objects::nonNull)
          .distinct()
          .toList();

      if (personIds.isEmpty()) {
        usuariosPage = Page.empty();
      } else {
        usuariosPage = usuarioRepository.findbyUsuarioPersonaFiltro(cuenta, estado, personIds,
            pageable);
        lstUsuarioDTO = armarUsuario(usuariosPage, lstPersonaResponse);
      }
    }

    return new PageImpl<>(lstUsuarioDTO, pageable, usuariosPage.getTotalElements());
  }

  private List<UsuarioPersonaResponse> armarUsuario(Page<UsuarioEntity> usuariosPage,
      List<PersonaResponse> lstPersonaDTO) {
    List<UsuarioPersonaResponse> lstUsuarioUsuarioPersonaResponse = new ArrayList<>();
    PersonaResponse personaResponse = null;

    for (UsuarioEntity usuarioEntity : usuariosPage.getContent()) {

      Optional<PersonaResponse> personasFiltradasDTO = lstPersonaDTO.stream()
          .filter(p -> usuarioEntity.getPersonaId().equals(p.id()))
          .findFirst(); // ← devuelve un Optional<PersonaDTO>
      if (personasFiltradasDTO.isPresent()) {
        personaResponse = personasFiltradasDTO.get();
      }

      UsuarioPersonaResponse usuarioDTO = new UsuarioPersonaResponse(
          usuarioEntity.getId(), personaResponse,
          usuarioEntity.getCuenta(), usuarioEntity.getClave(),
          usuarioEntity.getEstado()
      );

      lstUsuarioUsuarioPersonaResponse.add(usuarioDTO);
    }

    return lstUsuarioUsuarioPersonaResponse;
  }
  

  @Override
  public UsuarioDisponibleResponse verificarDisponibilidadUsuario(String cuenta) {
    var tokenAdmin = keycloakClient.obtenerTokenAdmin();
    var usuarioKeycloak = keycloakClient.buscarCuentaUsuario(cuenta, tokenAdmin);
    if (usuarioKeycloak == null) {
      return new UsuarioDisponibleResponse(Boolean.FALSE,
          "El usuario no existe en el Active Directory",
          null);
    }

    boolean usuarioRegistrado = usuarioRepository.findByUsuario(cuenta).isPresent();
    if (usuarioRegistrado) {
      return new UsuarioDisponibleResponse(Boolean.FALSE,
          "Ya existe un usuario con esta cuenta en el sistema.", null);
    }

    return new UsuarioDisponibleResponse(Boolean.TRUE,
        "El usuario está disponible en el Active Directory.", usuarioKeycloak);
  }

  @Transactional
  @Override
  public boolean crear(CrearUsuarioRequest request) {
    UUID personaId = null;
    try {
      var personaResponse = personaClient.crearPersona(request.persona());
      if (personaResponse == null || personaResponse.id() == null) {
        throw new BusinessException("El microservicio de personas no devolvió un Id válido");
      }
      personaId = personaResponse.id();

      var usuario = new CrearUsuarioDto(personaId, request.cuenta(), request.clave(), request.fechaCaducidad(),
          request.estado());
      var usuarioDto = usuarioMapper.toEntity(usuario);
      var usuarioGuardado = usuarioRepository.save(usuarioDto);

      if (usuarioGuardado == null) {
        throw new BusinessException("Error al guardar el usuario en la base de datos");
      }

      return true;

    } catch (Exception e) {
      if (personaId != null) {
        try {
          personaClient.eliminarPersona(personaId);
        } catch (Exception compensationEx) {
          log.error("Error durante la compensación al eliminar la persona {}: {}",
              personaId, compensationEx.getMessage());
        }
      }

      if (e instanceof BusinessException) {
        throw (BusinessException) e;
      }
      throw new BusinessException(
          "Error durante el proceso de creación de usuario: " + e.getMessage());
    }
  }

  @Transactional
  @Override
  public boolean update(UUID id, UsuarioPersonaRequest request) {
    UsuarioEntity prmUsuarioEntity = usuarioRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    PersonaResponse optPersonaResponse = personaClient.obtenerPorId(request.persona().id());
    if (optPersonaResponse == null) {
      throw new ResourceNotFoundException("Persona no encontrada");
    }
    
    List<ActualizarPersonaDocumentoRequest> dtoPersonaDocumentos =
            request.persona().personaDocumentos().stream()
                .map(doc -> new ActualizarPersonaDocumentoRequest(
                		doc.id(),
                		request.persona().id(),
                        doc.categoriaPersonaId(),
                        doc.tipoDocumento(),
                        doc.numeroDocumento(),
                        doc.estado()
                ))
                .toList();

    // Armar el DTO final para enviar al client
    var dto = new ActualizarPersonaRequest(
    		dtoPersonaDocumentos,
            request.persona().celular(),
            request.persona().estado()
            );


    PersonaResponse personaResponse = personaClient.actualizarPersona(optPersonaResponse.id(), dto);
    if (personaResponse == null) {
      throw new ResourceNotFoundException("Persona no actualizada");
    }

    prmUsuarioEntity.setClave(request.clave());
    prmUsuarioEntity.setPersonaId(optPersonaResponse.id());
    prmUsuarioEntity.setEstado(request.estado());
    usuarioRepository.save(prmUsuarioEntity);

    return true;
  }

  @Override
  public List<UsuarioComboResponse> findAllUsuarioSinRoles(String nombreUsuPersona) {
    List<UsuarioEntity> lstUsuarioEntity = new ArrayList<>();

    List<PersonaComboResponse> lstPersonaComboResponse = personaClient.obtenerPersonasPorNombre(
        "%" + nombreUsuPersona.trim().toUpperCase() + "%");

    if (lstPersonaComboResponse == null || lstPersonaComboResponse.isEmpty()) {
      lstUsuarioEntity = usuarioRepository.findbylikeUsuarioSinIDPersonasSinRoles(
          "%" + nombreUsuPersona.trim().toUpperCase() + "%");

      List<UUID> personIds = lstUsuarioEntity
          .stream()
          .map(UsuarioEntity::getPersonaId) // ← este getIddevuelve UUID con guiones
          .filter(Objects::nonNull)
          .distinct()
          .toList();

      lstPersonaComboResponse = personaClient.obtenerPersonasPorIdsConNombre(personIds);
    } else {
      List<UUID> personIds = lstPersonaComboResponse
          .stream()
          .map(PersonaComboResponse::id) // ← este getIddevuelve UUID con guiones
          .filter(Objects::nonNull)
          .distinct()
          .toList();

      lstUsuarioEntity = usuarioRepository.findbylikeUsuarioConIDPersonaSinRoles(
          "%" + nombreUsuPersona.trim().toUpperCase() + "%", personIds);
    }

    if (lstUsuarioEntity.isEmpty()) {
      throw new ResourceNotFoundException("No existen datos");
    }

    List<UsuarioComboResponse> lstUsuarioComboResponse = new ArrayList<>();

    for (UsuarioEntity item : lstUsuarioEntity) {
      Optional<PersonaComboResponse> personasFiltradasResponse = lstPersonaComboResponse.stream()
          .filter(p -> item.getPersonaId().equals(p.id()))
          .findFirst();

      PersonaComboResponse personaComboResponse = personasFiltradasResponse.orElse(null);
      UsuarioComboResponse usuarioComboDTO = new UsuarioComboResponse(item.getId(),
          personaComboResponse, item.getCuenta());
      lstUsuarioComboResponse.add(usuarioComboDTO);
    }
    return lstUsuarioComboResponse;
  }

  @Override
  public List<UsuarioComboResponse> listarTodosUsuarioConRol(String nombreUsuPersona) {
    List<UsuarioEntity> lstUsuarioEntity = new ArrayList<>();

    List<PersonaComboResponse> lstPersonaDTO = personaClient.obtenerPersonasPorNombre(
        "%" + nombreUsuPersona.trim().toUpperCase() + "%");

    if (lstPersonaDTO == null || lstPersonaDTO.isEmpty()) {
      lstUsuarioEntity = usuarioRepository.findbylikeUsuarioSinIDPersonaConRoles(
          "%" + nombreUsuPersona.trim().toUpperCase() + "%");

      List<UUID> personIds = lstUsuarioEntity
          .stream()
          .map(UsuarioEntity::getPersonaId) // ← este getIddevuelve UUID con guiones
          .filter(Objects::nonNull)
          .distinct()
          .toList();

      lstPersonaDTO = personaClient.obtenerPersonasPorIdsConNombre(personIds);
    } else {
      List<UUID> personIds = lstPersonaDTO
          .stream()
          .map(PersonaComboResponse::id) // ← este getIddevuelve UUID con guiones
          .filter(Objects::nonNull)
          .distinct()
          .toList();

      lstUsuarioEntity = usuarioRepository.findbylikeUsuarioConIDPersonaConRoles(
          "%" + nombreUsuPersona.trim().toUpperCase() + "%", personIds);
    }

    if (lstUsuarioEntity.isEmpty()) {
      throw new ResourceNotFoundException("No existen datos");
    }

    List<UsuarioComboResponse> lstUsuarioComboDTO = new ArrayList<>();

    for (UsuarioEntity item : lstUsuarioEntity) {
      Optional<PersonaComboResponse> personasFiltradasResponse = lstPersonaDTO.stream()
          .filter(p -> item.getPersonaId().equals(p.id()))
          .findFirst();

      PersonaComboResponse personaComboResponse = personasFiltradasResponse.orElse(null);
      UsuarioComboResponse usuarioComboDTO = new UsuarioComboResponse(item.getId(),
          personaComboResponse, item.getCuenta());
      lstUsuarioComboDTO.add(usuarioComboDTO);
    }
    return lstUsuarioComboDTO;
  }


}
