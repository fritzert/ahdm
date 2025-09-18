package pe.mef.sitfis.seguridad.application.service;

import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pe.mef.sitfis.seguridad.application.dto.MenuDto;
import pe.mef.sitfis.seguridad.application.mapper.MenuDomainMapper;
import pe.mef.sitfis.seguridad.application.port.inbound.ListarMenuUseCase;
import pe.mef.sitfis.seguridad.application.port.outbound.ListarMenuPort;
import pe.mef.sitfis.seguridad.application.query.ListarMenuApplicationQuery;
import pe.mef.sitfis.seguridad.domain.aggregate.MenuAggregate;

@RequiredArgsConstructor
@Slf4j
public class MenuService implements ListarMenuUseCase {

  private final ListarMenuPort listarMenuPort;
  private final MenuDomainMapper domainMapper;

  /*
  public MenuDto crear(CrearMenuCommand command) {
    // 1. Convertir Application → Domain Command
    var domainCommand = domainMapper.toDomainCommand(command);

    // 2. Crear aggregate con validaciones de dominio
    var aggregate = MenuAggregate.crear(domainCommand);

    // 3. Validaciones adicionales si es necesario
    validarReglasDominio(aggregate);

    // 4. Persistir
    var savedAggregate = listarMenuPort.guardar(aggregate);

    // 5. Convertir a DTO para retornar
    return domainMapper.toDto(savedAggregate);
  }
   */

  /**
   * Lista todos los menus filtrados por los criterios especificados.
   *
   * @param query consulta con criterios de filtrado
   * @return lista de menus que cumplen los criterios
   */
  @Override
  public List<MenuDto> listarTodosMenu(ListarMenuApplicationQuery query) {
    // 1. Convertir query Application → Domain
    var domainQuery = domainMapper.toDomainQuery(query);

    // 2. Obtener aggregates del puerto
    List<MenuAggregate> aggregates = listarMenuPort.listarTodosMenu(domainQuery);

    // 3. Aplicar reglas de presentación si es necesario
//    var filteredAggregates = aggregates.stream()
////        .filter(this::esVisibleParaUsuario)
//        .toList();

    // 4. Convertir a DTOs
    return aggregates.stream()
        .map(aggregate -> new MenuDto(
            aggregate.getId() != null ? aggregate.getId().valor() : null,
            aggregate.getNombre().valorFormateado(),
            aggregate.getOrden().valor()
        ))
        .toList();

    /*
    var domainQuery = domainMapper.toDomainQuery(query);
    List<MenuAggregate> aggregates = listarMenuPort.listarTodosMenu(domainQuery);
    return aggregates.stream()
        .map(domainMapper::toDto)
        .toList();
     */
  }

  /*
  private void validarReglasDominio(MenuAggregate aggregate) {
    // Validaciones complejas de dominio que no están en el aggregate
    if (aggregate.getNombre().valorFormateado().startsWith("ADMIN") &&
        aggregate.getOrden().valor() > 100) {
      throw new IllegalStateException("Menús admin deben tener orden <= 100");
    }
  }
   */

  /*
  private boolean esVisibleParaUsuario(MenuAggregate menu) {
    // Reglas de presentación
    return !menu.getNombre().valorFormateado().startsWith("HIDDEN");
  }
   */

}
