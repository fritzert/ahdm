package pe.mef.sitfis.seguridad.adapter.outbound.bd;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.RolJpaMapper;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.RolJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.RolGrupoRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.RolRepository;
import pe.mef.sitfis.seguridad.application.dto.ActualizarRolDto;
import pe.mef.sitfis.seguridad.application.dto.CrearRolDto;
import pe.mef.sitfis.seguridad.application.dto.RolComboDto;
import pe.mef.sitfis.seguridad.application.dto.RolDto;
import pe.mef.sitfis.seguridad.application.dto.RolInfoDto;
import pe.mef.sitfis.seguridad.application.dto.RolPaginadoDto;
import pe.mef.sitfis.seguridad.application.exception.BusinessException;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarRestriccionesPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarRolPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarRolPaginadoReportePort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarRolPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerRolPort;

@Component
@RequiredArgsConstructor
public class RolPersistenceAdapter implements
    ListarRolPort,
    ListarRolPaginadoReportePort,
    BuscarRolPaginadoPort,
    ObtenerRolPort,
    GuardarRolPort,
    ActualizarRolPort,
    EliminarRolPort,
    ListarRestriccionesPort {

  private final RolRepository repository;
  private final RolJpaMapper mapper;
  private final RolGrupoRepository rolGrupoRepository;

  @Override
  public List<RolComboDto> listar() {
    var query = repository.findAllAsCombo();
    return query.stream()
        .map(mapper::toComboDto)
        .toList();
  }

  @Override
  public List<RolPaginadoDto> listarTodoReporte() {
    var query = repository.findAll(Sort.by(Sort.Direction.DESC, "fechaModificacion"));
    return query.stream().map(mapper::toPaginadoDto).toList();
  }

  @Override
  public Pagina<RolPaginadoDto> buscarRolPaginado(String nombre, PaginaApplicationQuery query) {
    var pageable = PageRequest.of(query.pagina(), query.tamanio(),
        Sort.by(Sort.Direction.DESC, "fechaModificacion"));

    Page<RolJpaEntity> resultado;
    if (nombre == null || nombre.isBlank()) {
      resultado = repository.findAll(pageable);
    } else {
      resultado = repository.findAllByNombreContainingIgnoreCase(nombre, pageable);
    }
    var contenido = resultado.getContent().stream()
        .map(mapper::toPaginadoDto)
        .toList();

    return new Pagina<>(
        contenido,
        resultado.getNumber(),
        resultado.getTotalPages(),
        resultado.getTotalElements()
    );
  }

  @Override
  public RolDto obtenerPorId(Long id) {
    var query = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", id)));
    return mapper.toDto(query);
  }

  @Transactional
  @Override
  public void eliminar(Long id) {
    var query = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", id)));

    var relacionados = rolGrupoRepository.findByRolId(query.getId());
    if (!relacionados.isEmpty()) {
      throw new EntityExistsException("No es posible la eliminacion, el rol tiene datos relacionados.");
    }
    repository.deleteById(query.getId());
    //deleteDataCache();
  }

  @Transactional
  @Override
  public RolInfoDto guardar(CrearRolDto dto) {
    repository.findByNombreIgnoreCase(dto.nombre().trim())
        .ifPresent(g -> {
          throw new EntityExistsException("Ya existe el nombre registrado.");
        });

    var command = mapper.toEntity(dto);
    var resultado = repository.save(command);
    return mapper.toInfoDto(resultado);
  }

  @Transactional
  @Override
  public RolInfoDto actualizar(Long id, ActualizarRolDto dto) {
    var query = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", id)));

    if (dto.nombre().equalsIgnoreCase(query.getNombre())) {
      return mapper.toInfoDto(query);
    }

    repository.findByNombreIgnoreCase(dto.nombre().trim())
        .ifPresent(g -> {
          throw new EntityExistsException(
              "Ya existe un nombre o nombre corto (codigo) registrado.");
        });

    int filasActualizadas = repository.updateNombreById(
        dto.nombre().trim(), query.getId());

    if (filasActualizadas == 0) {
      throw new BusinessException("No se encontró ningún rol con el ID proporcionado.");
    }

    var grupoActualizado = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("No se pudo recuperar el rol actualizado."));

    return mapper.toInfoDto(grupoActualizado);
  }

  @Override
  public List<RolComboDto> listarRestricciones(Long id) {
    var query = repository.findAllAsComboRestriccion(id);
    return query.stream().map(mapper::toComboDto).toList();
  }
}
