package pe.mef.sitfis.seguridad.adapter.outbound.bd;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.ListaJpaMapper;
import pe.mef.sitfis.seguridad.adapter.inbound.api.mapper.ListaRolGrupoJpaMapper;
import pe.mef.sitfis.seguridad.application.query.Pagina;
import pe.mef.sitfis.seguridad.application.query.PaginaApplicationQuery;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.ListaJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.ListaRolGrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.ListaRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.ListaRolGrupoRepository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.RolGrupoRepository;
import pe.mef.sitfis.seguridad.application.dto.CrearListaRolGrupoDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoMenuDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoPaginadoDto;
import pe.mef.sitfis.seguridad.application.dto.ListaRolGrupoReporteDto;
import pe.mef.sitfis.seguridad.application.exception.BusinessException;
import pe.mef.sitfis.seguridad.application.port.outbound.EliminarListaRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.GuardarListaRolGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaPorMenuGrupoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaRolGrupoPaginadoPort;
import pe.mef.sitfis.seguridad.application.port.outbound.ObtenerListaRolGrupoReportePort;

@Component
@RequiredArgsConstructor
@Slf4j
public class ListaRolGrupoPersistenceAdapter implements
    ObtenerListaRolGrupoReportePort,
    ObtenerListaRolGrupoPaginadoPort,
    ObtenerListaPorMenuGrupoPort,
    GuardarListaRolGrupoPort,
    EliminarListaRolGrupoPort {

  private final ListaRolGrupoRepository repository;
  private final RolGrupoRepository rolGrupoRepository;
  private final ListaRepository listaRepository;

  private final ListaRolGrupoJpaMapper mapper;
  private final ListaJpaMapper listaMapper;

  @Override
  public List<ListaRolGrupoReporteDto> listarTodoReporte() {
    var query = repository.findAllReporte();
    return query.stream()
        .map(mapper::toReporteDto)
        .toList();
  }

  @Override
  public Pagina<ListaRolGrupoPaginadoDto> obtenerListaRolGrupoPaginado(Long grupoId, Long rolId,
      PaginaApplicationQuery query) {
    var pageable = PageRequest.of(query.pagina(), query.tamanio(),
        Sort.by(Sort.Direction.DESC, "fechaModificacion"));

    var resultado = repository.findListaByRolGrupoPaginado(grupoId, rolId, pageable);
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
  public List<ListaRolGrupoMenuDto> listarMenuGrupoRol(Long grupoId, Long rolId) {
    var query = repository.findListaMenuByRolGrupo(grupoId, rolId);
    return query.stream()
        .map(mapper::toGrupoMenuDto)
        .toList();
  }

  @Transactional
  @Override
  public void eliminar(UUID id) {
    var query = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(
            String.format("No se encontraron datos para el id: %s", id)));
    repository.deleteById(query.getId());
    //deleteDataCache();
  }

  @Transactional
  @Override
  public Boolean guardar(List<CrearListaRolGrupoDto> dtoList) {
    var rolGrupoId = dtoList.getFirst().rolGrupoId();
    var rolGrupo = rolGrupoRepository.findById(rolGrupoId)
        .orElseThrow(() ->
            new EntityNotFoundException(
                String.format("No se encontraron datos para el id: %s", rolGrupoId)));

    List<ListaJpaEntity> listaEntities = dtoList
        .stream()
        .map(x -> listaMapper.toEntity(x.lista())).toList();

    List<ListaJpaEntity> listaGuardadas = new ArrayList<>();

    for (ListaJpaEntity listaEntity : listaEntities) {
      boolean existeRegistro = repository.existsByMenuSubmenuTabRolGrupo(
          listaEntity.getMenu().getId(),
          listaEntity.getSubmenu().getId(),
          listaEntity.getTab().getId(),
          rolGrupo.getId());

      if (existeRegistro) {
        throw new BusinessException("La lista ya se encuentra registrada");
      }

      try {
        listaEntity = listaRepository.save(listaEntity);
        listaGuardadas.add(listaEntity);

        var listaRolGrupoEntity = new ListaRolGrupoJpaEntity();
        listaRolGrupoEntity.setLista(listaEntity);
        listaRolGrupoEntity.setRolGrupo(rolGrupo);
        repository.save(listaRolGrupoEntity);
      } catch (Exception e) {
        throw new BusinessException("Error al crear la lista: " + e.getMessage());
      }
    }

    return listaGuardadas.size() == dtoList.size();
  }

}
