package pe.mef.sitfis.seguridad.adapter.outbound.bd;

import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.GrupoJpaMapper;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.GrupoRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.RolGrupoRepository;
import pe.mef.sitfis.seguridad.application.dto.ActualizarGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.CrearGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoComboDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoInfoDto;
import pe.mef.sitfis.seguridad.application.dto.GrupoPaginadoDto;
import pe.mef.sitfis.seguridad.application.port.outbound.ActualizarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.BuscarGrupoPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarGrupoPaginadoReportePort;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerGrupoPort;

@Component
@RequiredArgsConstructor
@Slf4j
public class GrupoPersistenceAdapter implements
    ListarGrupoPort,
    ListarGrupoPaginadoReportePort,
    BuscarGrupoPaginadoPort,
    ObtenerGrupoPort,
    GuardarGrupoPort,
    ActualizarGrupoPort,
    EliminarGrupoPort {

  private final GrupoRepository repository;
  private final GrupoJpaMapper mapper;
  private final RolGrupoRepository rolGrupoRepository;

  @Override
  public List<GrupoComboDto> listar() {
    var query = repository.findAllAsCombo();
    return query.stream()
        .map(mapper::toComboDto)
        .toList();
  }

  @Override
  public List<GrupoPaginadoDto> listarTodoReporte() {
    var query = repository.findAll(Sort.by(Sort.Direction.DESC, "fechaModificacion"));
    return query.stream().map(mapper::toPaginadoDto).toList();
  }

  @Override
  public Pagina<GrupoPaginadoDto> buscarGrupoPaginado(String nombre, Long grupoId, Long rolId,
      PaginaApplicationQuery query) {
    var pageable = PageRequest.of(query.pagina(), query.tamanio(),
        Sort.by(Sort.Direction.DESC, "fechaModificacion"));

    var resultado = repository.findByNombreGrupoIdRolIdPaginado(nombre,
        grupoId,
        rolId, pageable);
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
  public GrupoDto obtenerPorId(Long id) {
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

    var relacionados = rolGrupoRepository.findByGrupoId(query.getId());
    if (!relacionados.isEmpty()) {
      throw new EntityExistsException(
          "No es posible la eliminacion, el grupo tiene datos relacionados.");
    }
    repository.deleteById(query.getId());
    //deleteDataCache();
  }

  @Transactional
  @Override
  public GrupoInfoDto guardar(CrearGrupoDto dto) {
    repository.findByNombreIgnoreCaseOrCodigoIgnoreCase(
            dto.nombre().trim(), dto.codigo().trim())
        .ifPresent(g -> {
          throw new EntityExistsException("Ya existe el nombre registrado.");
        });

    var command = mapper.toEntity(dto);
    var resultado = repository.save(command);
    return mapper.toInfoDto(resultado);
  }

  @Transactional
  @Override
  public GrupoInfoDto actualizar(Long id, ActualizarGrupoDto dto) {
    var query = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", id)));

    if (dto.nombre().equalsIgnoreCase(query.getNombre()) &&
        dto.codigo().equalsIgnoreCase(query.getCodigo())) {
      log.info("AMBOS NOMBRES SON IGUALES");
      return mapper.toInfoDto(query);
    }

    var lista = repository.findByNombreCodigoIgnorandoId(
        dto.nombre().trim(), dto.codigo().trim(), query.getId());

    if (!lista.isEmpty()) {
      throw new EntityExistsException("Ya existe un nombre o nombre corto (codigo) registrado.");
    }

    int filasActualizadas = repository.updateNombreCodigoById(
        dto.nombre().trim(), dto.codigo().trim(), query.getId());

    if (filasActualizadas == 0) {
      throw new EntityNotFoundException("No se encontró ningún grupo con el ID proporcionado.");
    }

    var grupoActualizado = repository.findById(id)
        .orElseThrow(
            () -> new EntityNotFoundException("No se pudo recuperar el grupo actualizado."));

    return mapper.toInfoDto(grupoActualizado);
  }


}
