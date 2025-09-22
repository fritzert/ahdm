package pe.mef.sitfis.seguridad.adapter.outbound.bd;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.mapper.MenuJpaMapper;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.MenuRepository;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarMenuPort;
import pe.mef.sitfis.seguridad.domain.aggregate.MenuAggregate;
import pe.mef.sitfis.seguridad.domain.query.ListarMenuDomainQuery;

@Component
@RequiredArgsConstructor
public class MenuPersistenceAdapter implements ListarMenuPort {

  private final MenuRepository repository;
  private final MenuJpaMapper mapper;

  /**
   * Lista todos los menus aplicando el filtro especificado.
   *
   * @param query consulta de dominio con criterios de filtrado
   * @return lista de agregados de menu
   */
  @Override
  public List<MenuAggregate> listarTodosMenu(ListarMenuDomainQuery query) {
    var entities = repository.findByNombreLike(query.criterioNombre());
    return entities.stream().map(mapper::toAggregate).toList();

    /*
    return entities.stream()
        .map(entity -> MenuAggregate.reconstruir(
            MenuId.de(entity.getId()),
            new MenuNombreValue(entity.getNombre()),
            new MenuOrdenValue(entity.getOrden())
        ))
        .toList();
    */

    /*
    return entities.stream()
        .map(mapper::toDto)
        .toList();
     */
  }

}
