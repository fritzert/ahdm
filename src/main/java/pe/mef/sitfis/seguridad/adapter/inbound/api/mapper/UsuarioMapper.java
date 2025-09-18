package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.CrearUsuarioDto;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioResponse;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UsuarioMapper {

  UsuarioResponse toDTO(UsuarioEntity usuarioEntity);

  List<UsuarioResponse> toDTO(List<UsuarioEntity> lstUsuarioEntity);

  UsuarioEntity toEntity(CrearUsuarioDto dto);
}
