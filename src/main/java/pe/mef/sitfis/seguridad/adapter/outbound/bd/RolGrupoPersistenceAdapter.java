package pe.mef.sitfis.seguridad.adapter.outbound.bd;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.RolGrupoJpaMapper;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.RolGrupoRepository;
import pe.mef.sitfis.seguridad.application.command.ActualizarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.EliminarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.ObtenerRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoInfoDto;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarRolGrupoPorGrupoIdPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerRolGrupoPort;
import pe.mef.sitfis.seguridad.application.query.ListarRolGrupoApplicationQuery;

@Slf4j
@Component
@RequiredArgsConstructor
public class RolGrupoPersistenceAdapter implements
    BuscarRolGrupoPorGrupoIdPort,
    ObtenerRolGrupoPort,
//    GuardarActualizarRolGrupoPort,
    GuardarRolGrupoPort,
    ActualizarRolGrupoPort,
    EliminarRolGrupoPort {

  private final RolGrupoRepository repository;
  private final RolGrupoJpaMapper mapper;

  @Override
  public List<RolGrupoDto> buscarPorGrupoId(ListarRolGrupoApplicationQuery query) {
    var grupoId = query.id();
    if (grupoId == null) {
      throw new IllegalArgumentException("El grupoId es obligatorio.");
    }

    var resultado = repository.findAllByGrupoId(query.id());
    log.info("resultado: " + resultado.size());
    return resultado.stream()
        .map(mapper::toDto)
        .toList();
  }

  @Override
  public RolGrupoInfoDto obtenerPorId(ObtenerRolGrupoCommand command) { // ← ApplicationCommand
    var entity = repository.findById(command.id())
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", command.id())));

//    return aggregateMapper.toAggregate(entity);
    return mapper.toInfoDto(entity);
  }

//  @Transactional
//  @Override
//  public int guardarActualizar(List<CrearRolGrupoCommand> grupoCommandList) {
//    var listaNuevos = grupoCommandList.stream().filter(x -> x.id() == null).toList();
//    var commandNuevos = listaNuevos.stream()
//        .map(mapper::toEntity)
//        .toList();
//    var resultadoNuevos = repository.saveAll(commandNuevos);
//
//    var listaActualizados = grupoCommandList.stream().filter(x -> x.id() != null).toList();
//    for (var rolGrupoCommand : listaActualizados) {
//      repository.actualizarRolGrupoConRecord(rolGrupoCommand);
//    }
//
//    return resultadoNuevos.size() + listaActualizados.size();
//  }

  @Transactional
  @Override
  public RolGrupoInfoDto guardar(CrearRolGrupoCommand command) {
    // Validar que no existe la misma combinación rol-grupo
    repository.findByRolIdAndGrupoId(command.rolId(), command.grupoId())
        .ifPresent(rg -> {
          throw new EntityExistsException(
              "Ya existe una relación entre el rol " + command.rolId() +
                  " y el grupo " + command.grupoId());
        });

    // Crear entity desde command
    var entity = mapper.toEntity(command);
    var resultado = repository.save(entity);

    // Validaciones de infrastructure
//    repository.findByRolIdAndGrupoId(command.rolId(), command.grupoId())
//        .ifPresent(rg -> {
//          throw new EntityExistsException(
//              "Ya existe una relación entre el rol " + command.rolId() +
//                  " y el grupo " + command.grupoId());
//        });
//
//    // Crear entity desde command
//    var entity = mapper.toEntity(command);
//    var savedEntity = repository.save(entity);
//
//    return aggregateMapper.toAggregate(savedEntity);

    // Validar que el rol existe
//    if (!repository.existsById(dto.rolId())) {
//      throw new EntityNotFoundException("No se encontró el rol con ID: " + dto.rolId());
//    }
//
//    // Validar que el grupo existe
//    if (!repository.existsById(dto.grupoId())) {
//      throw new EntityNotFoundException("No se encontró el grupo con ID: " + dto.grupoId());
//    }

//    var entity = mapper.toEntity(dto);
//    var resultado = repository.save(entity);
    return mapper.toInfoDto(resultado);
  }

  @Transactional
  @Override
  public RolGrupoInfoDto actualizar(ActualizarRolGrupoCommand command) {
    // 1. Verificar que existe
    var entity = repository.findById(command.id())
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", command.id())));

    // 2. Verificar si la combinación rol-grupo cambió y ya existe
    if (!entity.getRoles().getId().equals(command.rolId()) ||
        !entity.getGrupos().getId().equals(command.grupoId())) {

      repository.findByRolIdAndGrupoId(command.rolId(), command.grupoId())
          .filter(rg -> !rg.getId().equals(entity.getId()))
          .ifPresent(rg -> {
            throw new EntityExistsException(
                "Ya existe otra relación entre el rol " + command.rolId() +
                    " y el grupo " + command.grupoId());
          });
    }

    // 3. Ejecutar actualización
    int filasActualizadas = repository.actualizarRolGrupo(
        command.id(),
        command.rolId(),
        command.grupoId(),
        command.flagRestriccion(),
        command.flagConsulta(),
        command.flagOperacion(),
        command.flagAsignarRecursos(),
        command.flagEnviarBandeja(),
        command.flagEnviarEtapa(),
        command.flagAdjuntarArchivo()
    );

    // 4. Verificar que se actualizó
    if (filasActualizadas == 0) {
      throw new EntityNotFoundException("No se pudo actualizar el RolGrupo.");
    }

    // 5. Obtener entidad actualizada y convertir a dto
    var updatedEntity = repository.findById(command.id())
        .orElseThrow(
            () -> new EntityNotFoundException("No se pudo recuperar el RolGrupo actualizado."));

    return mapper.toInfoDto(updatedEntity);
  }

  @Transactional
  @Override
  public void eliminar(EliminarRolGrupoCommand command) {
    var entity = repository.findById(command.id())
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", command.id())));

    // Validaciones adicionales a nivel de infraestructura
    if (repository.existsRelatedEntities(entity.getId())) {
      throw new EntityExistsException(
          "No es posible la eliminación, el rol-grupo tiene datos relacionados.");
    }

    repository.deleteById(entity.getId());
  }
}
