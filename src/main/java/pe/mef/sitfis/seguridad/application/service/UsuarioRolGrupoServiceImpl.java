package pe.mef.sitfis.seguridad.application.service;

import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.EliminarMultipleUsuarioRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.PersonaDocumentoResponse;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.dto.PersonaResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioCopiarRolRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoDTO;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoPaginadoResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoPrincipalRequest;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.GrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioRolGrupoEntity;
import pe.mef.sitfis.seguridad.application.exception.BusinessException;
import pe.mef.sitfis.seguridad.adapter.outbound.persona.client.PersonaClient;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.UsuarioMapper;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.UsuarioRolGrupoMapper;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.GrupoRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.UsuarioRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.UsuarioRolGrupoRepository;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.application.exception.ServiceException;

@RequiredArgsConstructor
@Service
@Slf4j
public class UsuarioRolGrupoServiceImpl implements UsuarioRolGrupoService {

  private final UsuarioRolGrupoRepository usuarioRolGrupoRepository;
  private final UsuarioRepository usuarioRepository;
  private final GrupoRepository grupoRepository;
  private final UsuarioRolGrupoMapper usuarioRolGrupoMapper;
  private final UsuarioMapper usuarioMapper;
  private final PersonaClient personaClient;

  @Override
  public Pagina<UsuarioRolGrupoPaginadoResponse> buscarUsuarioRolGrupoPaginado(String cuenta,
      Long tipoDocumentoId, String numeroDocumento, PaginaApplicationQuery query) {
    Sort.Direction direction = query.esAscendente() ? Sort.Direction.ASC : Sort.Direction.DESC;
    Pageable pageable = PageRequest.of(query.pagina(), query.tamanio(),
        Sort.by(direction, query.ordenadoPor()));
    List<PersonaResponse> lstPersonaResponse = new ArrayList<>();
    Page<UsuarioEntity> usuariosPage = Page.empty();

    if (tipoDocumentoId.toString().trim().equals("-1") && (numeroDocumento == null || numeroDocumento.trim().isEmpty())
    		) {

      // Obtener usuarios paginados
      cuenta = (cuenta != null) ? "%" + cuenta.trim().toUpperCase() + "%" : null;
      usuariosPage = usuarioRepository.findAllPaginacionFiltroCuenta(pageable, cuenta);
      List<UsuarioResponse> usuarioDTOList = usuariosPage.stream().map(usuarioMapper::toDTO)
          .toList();

      // Obtener IDs de usuarios
      List<UUID> listaIds = usuarioDTOList.stream().map(UsuarioResponse::id).toList();

      // Obtener relaciones UsuarioRolGrupo
      List<UsuarioRolGrupoEntity> listaUsuarioRolGrupo = usuarioRolGrupoRepository.findByIdsUsuarios(
          listaIds);

      List<UUID> lstPersonIds = listaUsuarioRolGrupo.stream()
          .map(UsuarioRolGrupoEntity::getUsuario)
          .filter(Objects::nonNull)
          .map(UsuarioEntity::getPersonaId)
          .filter(Objects::nonNull)
          .distinct()
          .collect(Collectors.toList());

      lstPersonaResponse = personaClient.obtenerPersonasPorIds(lstPersonIds);

      // Mapear PersonaResponse por personaId
      Map<UUID, PersonaResponse> personaMap = lstPersonaResponse.stream()
          .filter(p -> p.id() != null)
          .collect(Collectors.toMap(PersonaResponse::id, Function.identity()));

      // Agrupar por ID de usuario
      Map<UUID, List<UsuarioRolGrupoEntity>> agrupadoPorUsuario = listaUsuarioRolGrupo.stream()
          .collect(Collectors.groupingBy(urg -> urg.getUsuario().getId()));

      // Construir la respuesta por usuario
      List<UsuarioRolGrupoPaginadoResponse> respuesta = usuarioDTOList.stream()
          .map(usuarioDto -> {
            List<UsuarioRolGrupoEntity> entidadesUsuario = agrupadoPorUsuario.getOrDefault(
                usuarioDto.id(), List.of());

            // Agrupar por ID de grupo (Long)
            Map<Long, List<UsuarioRolGrupoEntity>> agrupadoPorGrupo = entidadesUsuario.stream()
                .filter(e -> e.getRolGrupo() != null &&
                             e.getRolGrupo().getGrupos() != null &&
                             e.getRolGrupo().getGrupos().getId() != null)
                .collect(Collectors.groupingBy(e -> e.getRolGrupo().getGrupos().getId()));

            // Construir GrupoRecord
            List<UsuarioRolGrupoPaginadoResponse.GrupoRecord> grupos = agrupadoPorGrupo.entrySet()
                .stream()
                .map(entry -> {
                  Long grupoId = entry.getKey();
                  List<UsuarioRolGrupoEntity> entidadesGrupo = entry.getValue();
                  String grupoNombre = entidadesGrupo.get(0).getRolGrupo().getGrupos().getNombre();

                  // Construir RolRecord
                  List<UsuarioRolGrupoPaginadoResponse.RolRecord> roles = entidadesGrupo.stream()
                      .map(e -> new UsuarioRolGrupoPaginadoResponse.RolRecord(
                          e.getId(),
                          e.getRolGrupo().getRoles().getId(),
                          e.getRolGrupo().getRoles().getNombre()
                      ))
                      .toList();

                  return new UsuarioRolGrupoPaginadoResponse.GrupoRecord(
                      entidadesGrupo.get(0).getId(),
                      grupoId,
                      grupoNombre,
                      roles
                  );
                })
                .toList();

            // Obtener persona asociada (si existe)
            UUID personaId = listaUsuarioRolGrupo.stream()
                .filter(urg -> urg.getUsuario().getId().equals(usuarioDto.id()))
                .map(urg -> urg.getUsuario().getPersonaId())
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

            /*PersonaResponse persona = personaMap.get(personaId);
            String tipoDocumentoStr =
                persona != null && persona.tipoDocumento() != null ? persona.tipoDocumento()
                    .nombre() : "sin-doc";
            String numeroDocumentoStr =
                persona != null && persona.numeroDocumento() != null ? persona.numeroDocumento()
                    : "sin-numero";*/
            
            PersonaResponse persona = personaMap.get(personaId);

            String tipoDocumentoStr = "sin-doc";
            String numeroDocumentoStr = "sin-numero";

            if (persona != null && persona.personaDocumentos() != null && !persona.personaDocumentos().isEmpty()) {
                PersonaDocumentoResponse primerDoc = persona.personaDocumentos().get(0);

                if (primerDoc.tipoDocumento() != null) {
                    tipoDocumentoStr = primerDoc.tipoDocumento().nombre();
                }
                if (primerDoc.numeroDocumento() != null) {
                    numeroDocumentoStr = primerDoc.numeroDocumento();
                }
            }

            return new UsuarioRolGrupoPaginadoResponse(
                usuarioDto.id(),
                usuarioDto.cuenta(),
                tipoDocumentoStr,
                numeroDocumentoStr,
                usuarioDto.estado(),
                usuarioDto.usuarioModificacion(),
                usuarioDto.fechaModificacion(),
                grupos
            );
          })
          .toList();

      return new Pagina<>(
          respuesta,
          usuariosPage.getNumber(),
          usuariosPage.getTotalPages(),
          (int) usuariosPage.getTotalElements()
      );
    } else {

      lstPersonaResponse = personaClient.buscarPersonasPorParametros(
          null,
          tipoDocumentoId,
          numeroDocumento);

      List<UUID> personIds = lstPersonaResponse
          .stream()
          .map(PersonaResponse::id) // ← este getIddevuelve UUID con guiones
          .filter(Objects::nonNull)
          .distinct()
          .collect(Collectors.toList());

      if (personIds.isEmpty()) {
        usuariosPage = Page.empty();
      } else {
        cuenta = (cuenta != null) ? "%" + cuenta.trim().toUpperCase() + "%" : null;
        usuariosPage = usuarioRepository.findAllPaginacionFiltroCuentaPersonId(pageable, cuenta,
            personIds);
      }

      List<UsuarioResponse> usuarioDTOList = usuariosPage.stream().map(usuarioMapper::toDTO)
          .toList();

      // Obtener IDs de usuarios
      List<UUID> listaIds = usuarioDTOList.stream().map(UsuarioResponse::id).toList();

      // Obtener relaciones UsuarioRolGrupo
      List<UsuarioRolGrupoEntity> listaUsuarioRolGrupo = usuarioRolGrupoRepository.findByIdsUsuarios(
          listaIds);

      List<UUID> lstPersonIds = listaUsuarioRolGrupo.stream()
          .map(UsuarioRolGrupoEntity::getUsuario)
          .filter(Objects::nonNull)
          .map(UsuarioEntity::getPersonaId)
          .filter(Objects::nonNull)
          .distinct()
          .collect(Collectors.toList());

      lstPersonaResponse = personaClient.obtenerPersonasPorIds(lstPersonIds);

      // Mapear PersonaResponse por personaId
      Map<UUID, PersonaResponse> personaMap = lstPersonaResponse.stream()
          .filter(p -> p.id() != null)
          .collect(Collectors.toMap(PersonaResponse::id, Function.identity()));

      // Agrupar por ID de usuario
      Map<UUID, List<UsuarioRolGrupoEntity>> agrupadoPorUsuario = listaUsuarioRolGrupo.stream()
          .collect(Collectors.groupingBy(urg -> urg.getUsuario().getId()));

      // Construir la respuesta por usuario
      List<UsuarioRolGrupoPaginadoResponse> respuesta = usuarioDTOList.stream()
          .map(usuarioDto -> {
            List<UsuarioRolGrupoEntity> entidadesUsuario = agrupadoPorUsuario.getOrDefault(
                usuarioDto.id(), List.of());

            // Agrupar por ID de grupo (Long)
            Map<Long, List<UsuarioRolGrupoEntity>> agrupadoPorGrupo = entidadesUsuario.stream()
                .filter(e -> e.getRolGrupo() != null &&
                             e.getRolGrupo().getGrupos() != null &&
                             e.getRolGrupo().getGrupos().getId() != null)
                .collect(Collectors.groupingBy(e -> e.getRolGrupo().getGrupos().getId()));

            // Construir GrupoRecord
            List<UsuarioRolGrupoPaginadoResponse.GrupoRecord> grupos = agrupadoPorGrupo.entrySet()
                .stream()
                .map(entry -> {
                  Long grupoId = entry.getKey();
                  List<UsuarioRolGrupoEntity> entidadesGrupo = entry.getValue();
                  String grupoNombre = entidadesGrupo.get(0).getRolGrupo().getGrupos().getNombre();

                  // Construir RolRecord
                  List<UsuarioRolGrupoPaginadoResponse.RolRecord> roles = entidadesGrupo.stream()
                      .map(e -> new UsuarioRolGrupoPaginadoResponse.RolRecord(
                          e.getId(),
                          e.getRolGrupo().getRoles().getId(),
                          e.getRolGrupo().getRoles().getNombre()
                      ))
                      .toList();

                  return new UsuarioRolGrupoPaginadoResponse.GrupoRecord(
                      entidadesGrupo.get(0).getId(),
                      grupoId,
                      grupoNombre,
                      roles
                  );
                })
                .toList();

            // Obtener persona asociada (si existe)
            UUID personaId = listaUsuarioRolGrupo.stream()
                .filter(urg -> urg.getUsuario().getId().equals(usuarioDto.id()))
                .map(urg -> urg.getUsuario().getPersonaId())
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

            /*PersonaResponse persona = personaMap.get(personaId);
            String tipoDocumentoStr =
                persona != null && persona.tipoDocumento() != null ? persona.tipoDocumento()
                    .nombre() : "sin-doc";
            String numeroDocumentoStr =
                persona != null && persona.numeroDocumento() != null ? persona.numeroDocumento()
                    : "sin-numero";*/
            
            PersonaResponse persona = personaMap.get(personaId);

            String tipoDocumentoStr = "sin-doc";
            String numeroDocumentoStr = "sin-numero";

            if (persona != null && persona.personaDocumentos() != null && !persona.personaDocumentos().isEmpty()) {
                PersonaDocumentoResponse primerDoc = persona.personaDocumentos().get(0);

                if (primerDoc.tipoDocumento() != null) {
                    tipoDocumentoStr = primerDoc.tipoDocumento().nombre();
                }
                if (primerDoc.numeroDocumento() != null) {
                    numeroDocumentoStr = primerDoc.numeroDocumento();
                }
            }

            return new UsuarioRolGrupoPaginadoResponse(
                usuarioDto.id(),
                usuarioDto.cuenta(),
                tipoDocumentoStr,
                numeroDocumentoStr,
                usuarioDto.estado(),
                usuarioDto.usuarioModificacion(),
                usuarioDto.fechaModificacion(),
                grupos
            );
          })
          .toList();

      return new Pagina<>(
          respuesta,
          usuariosPage.getNumber(),
          usuariosPage.getTotalPages(),
          (int) usuariosPage.getTotalElements()
      );
    }

  }

  @Override
  public List<UsuarioRolGrupoDTO> findByIDUsuario(UUID usuarioId) throws ServiceException {
    UsuarioEntity usuarioEntity = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    return usuarioRolGrupoRepository.findByUsuario(usuarioEntity).stream()
        .map(usuarioRolGrupoMapper::toDTO).toList();
  }

  @Transactional
  @Override
  public boolean grabarCopiarRoles(UsuarioCopiarRolRequest request) {

    UUID varUsuarioSinRoles = request.idUsuarioSinRoles();
    usuarioRepository.findById(varUsuarioSinRoles)
        .orElseThrow(() -> new RuntimeException("Usuario Sin Roles no encontrado"));

    UUID varUsuarioConRoles = request.idUsuarioConRoles();
    usuarioRepository.findById(varUsuarioConRoles)
        .orElseThrow(() -> new RuntimeException("Usuario Con Roles no encontrado"));

    String usuarioActual = SecurityContextHolder.getContext().getAuthentication().getName();

    usuarioRolGrupoRepository.copiarUsuarioRolGrupo(varUsuarioSinRoles, varUsuarioConRoles,
        usuarioActual);

    return true;
  }

  @Transactional
  @Override
  public boolean crearActualizarUsuarioRolGrupo(List<UsuarioRolGrupoRequest> request) {
    var listaNuevos = request.stream().filter(x -> x.id() == null).toList();
    var listaActualizados = request.stream().filter(x -> x.id() != null).toList();

    var commandNuevos = usuarioRolGrupoMapper.toEntityList(listaNuevos);
    var resultadoNuevos = usuarioRolGrupoRepository.saveAll(commandNuevos);

    for (var requests : listaActualizados) {
      usuarioRolGrupoRepository.actualizarUsuarioRolGrupoConRecord(requests);
    }

    return request.size() == (resultadoNuevos.size() + listaActualizados.size());
  }

  @Transactional
  @Override
  public boolean actualizarUsuarioRolGrupoPrincipal(UUID usuarioId,
      UsuarioRolGrupoPrincipalRequest request) {
    UsuarioEntity userEntity = usuarioRepository.findById(usuarioId)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

    Long varEsGrupoIDPrincipal = request.grupoId();
    GrupoJpaEntity grupoEntity = grupoRepository.findById(varEsGrupoIDPrincipal)
        .orElseThrow(() -> new RuntimeException("Grupo no encontrado"));
    int filasActualizadas = usuarioRepository.upd_grupoIDPrincipal(userEntity.getId(),
        grupoEntity.getId());

    return filasActualizadas > 0;
  }

  @Transactional
  @Override
  public void eliminarMultipleUsuarioRolGrupo(EliminarMultipleUsuarioRolGrupoRequest request) {
    List<UUID> uuids = request.uuids();
    var query = usuarioRolGrupoRepository.findByIds(uuids);
    if (query.isEmpty()) {
      throw new BusinessException(String.format("No se encontraron datos para los ids: %s", uuids));
    }
    usuarioRolGrupoRepository.eliminarMultipleUsuarioRolGrupo(uuids);
  }


}

