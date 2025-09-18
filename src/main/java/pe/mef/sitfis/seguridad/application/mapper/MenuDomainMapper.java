package pe.mef.sitfis.seguridad.application.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import pe.mef.sitfis.seguridad.application.query.ListarMenuApplicationQuery;
import pe.mef.sitfis.seguridad.domain.query.ListarMenuDomainQuery;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, nullValueMappingStrategy = NullValueMappingStrategy.RETURN_DEFAULT)
public interface MenuDomainMapper {

  MenuDomainMapper INSTANCE = Mappers.getMapper(MenuDomainMapper.class);

  @Mapping(target = "criterioNombre", source = "nombre")
  ListarMenuDomainQuery toDomainQuery(ListarMenuApplicationQuery applicationQuery);

  //+++++++++++++++++++++++++++++++++++++++++++++++++++++
  // Aggregate → DTO
//  public MenuDto toDto(MenuAggregate aggregate) {
//    return new MenuDto(
//        aggregate.getId() != null ? aggregate.getId().valor() : null,
//        aggregate.getNombre().valorFormateado(),
//        aggregate.getOrden().valor()
//    );
//  }

  // DTO → Aggregate
//  public MenuAggregate toAggregate(MenuDto dto) {
//    return MenuAggregate.reconstruir(
//        MenuId.de(dto.id()),
//        MenuNombreValue.de(dto.nombre()),
//        MenuOrdenValue.de(dto.orden())
//    );
//  }

  // Application Command → Domain Command
//  public CrearMenuDomainCommand toDomainCommand(CrearMenuCommand command) {
//    return new CrearMenuDomainCommand(
//        MenuNombreValue.de(command.nombre()),
//        MenuOrdenValue.de(command.orden())
//    );
//  }

  // Application Query → Domain Query
//  public ListarMenuDomainQuery toDomainQuery(ListarMenuApplicationQuery query) {
//    return ListarMenuDomainQuery.conNombre(query.nombre());
//  }

}