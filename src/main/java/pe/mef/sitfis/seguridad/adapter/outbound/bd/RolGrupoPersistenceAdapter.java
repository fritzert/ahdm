package pe.mef.sitfis.seguridad.adapter.outbound.bd;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper.RolGrupoJpaMapper;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.GrupoRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.RolGrupoRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.RolRepository;
import pe.mef.sitfis.seguridad.application.command.EliminarRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.command.ObtenerRolGrupoCommand;
import pe.mef.sitfis.seguridad.application.dto.RolGrupoDto;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarRolGrupoPorGrupoIdPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerRolGrupoPort;
import pe.mef.sitfis.seguridad.domain.aggregate.RolGrupoAggregate;
import pe.mef.sitfis.seguridad.domain.command.ActualizarRolGrupoDomainCommand;
import pe.mef.sitfis.seguridad.domain.command.CrearRolGrupoDomainCommand;
import pe.mef.sitfis.seguridad.domain.query.ListarRolGrupoDomainQuery;

@Slf4j
@Component
@RequiredArgsConstructor
public class RolGrupoPersistenceAdapter implements
    BuscarRolGrupoPorGrupoIdPort,
    ObtenerRolGrupoPort,
    GuardarRolGrupoPort,
    ActualizarRolGrupoPort,
    EliminarRolGrupoPort {

  private final RolGrupoRepository repository;
  private final RolRepository rolRepository;
  private final GrupoRepository grupoRepository;
  private final RolGrupoJpaMapper mapper;

  @Override
  public List<RolGrupoDto> buscarPorGrupoId(ListarRolGrupoDomainQuery query) {
    var grupoId = query.id();
    if (grupoId == null) {
      throw new IllegalArgumentException("El grupoId es obligatorio.");
    }

    var resultado = repository.findAllByGrupoId(query.id());
    return resultado.stream()
        .map(mapper::toDto)
        .toList();
  }

  @Override
  public RolGrupoAggregate obtenerPorId(ObtenerRolGrupoCommand command) {
    var entity = repository.findById(command.id())
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", command.id())));

    return mapper.toAggregate(entity);
  }

  @Transactional
  @Override
  public RolGrupoAggregate guardar(CrearRolGrupoDomainCommand command) {
    Long rolId = command.rolId().valor();
    if (!rolRepository.existsById(rolId)) {
      throw new EntityNotFoundException("No se encontró el rol con ID: " + rolId);
    }

    Long grupoId = command.grupoId().valor();
    if (!grupoRepository.existsById(grupoId)) {
      throw new EntityNotFoundException("No se encontró el grupo con ID: " + grupoId);
    }

    repository.findByRolIdAndGrupoId(rolId, grupoId)
        .ifPresent(rg -> {
          throw new EntityExistsException(
              "Ya existe una relación entre el rol " + rolId + " y el grupo " + grupoId);
        });

    var entity = mapper.toEntity(command);
    var resultado = repository.save(entity);
    return mapper.toAggregate(resultado);
  }

  @Transactional
  @Override
  public RolGrupoAggregate actualizar(ActualizarRolGrupoDomainCommand command) {
    var entity = repository.findById(command.id().valor())
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", command.id())));

    Long rolId = command.rolId().valor();
    Long grupoId = command.grupoId().valor();
    if (!entity.getRoles().getId().equals(rolId) ||
        !entity.getGrupos().getId().equals(grupoId)) {
      repository.findByRolIdAndGrupoId(rolId, grupoId)
          .filter(rg -> !rg.getId().equals(entity.getId()))
          .ifPresent(rg -> {
            throw new EntityExistsException(
                "Ya existe otra relación entre el rol " + command.rolId() +
                    " y el grupo " + command.grupoId());
          });
    }

    int filasActualizadas = repository.actualizarRolGrupo(
        command.id().valor(),
        command.rolId().valor(),
        command.grupoId().valor(),
        command.flagRestriccion().valor(),
        command.flagConsulta().valor(),
        command.flagOperacion().valor(),
        command.flagAsignarRecursos().valor(),
        command.flagEnviarBandeja().valor(),
        command.flagEnviarEtapa().valor(),
        command.flagAdjuntarArchivo().valor()
    );

    if (filasActualizadas == 0) {
      throw new EntityNotFoundException("No se pudo actualizar el RolGrupo.");
    }

    var resultado = repository.findById(command.id().valor())
        .orElseThrow(
            () -> new EntityNotFoundException("No se pudo recuperar el RolGrupo actualizado."));

    return mapper.toAggregate(resultado);
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
