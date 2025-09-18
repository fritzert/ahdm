package pe.mef.sitfis.seguridad.adapter.inbound.api.mapper;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.mapstruct.Mapper;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.LoginRolesMenuDTO;

@Mapper(componentModel = "spring")
public interface LoginRolesMenuMapper {

  default LoginRolesMenuDTO toDto(Object[] data) {
      if (data == null || data.length < 19) {
          return null;
      }

    return LoginRolesMenuDTO.builder()
        .idUsuario(toUUID(data[0]))
        .usuario((String) data[1])
        .idPersona(toUUID(data[2]))
        .grupoIdPrincipal(toLong(data[3]))
        .idRolGrupo(toLong(data[4]))
        .idGrupo(toLong(data[5]))
        .grupo((String) data[6])
        .idRol(toLong(data[7]))
        .rol((String) data[8])
        .idMenu(toLong(data[9]))
        .menu((String) data[10])
        .ordenMenu(toInt(data[11]))
        .idSubmenu(toLong(data[12]))
        .submenu((String) data[13])
        .nivel(toInt(data[14]))
        .ordenSubmenu(toInt(data[15]))
        .idTab(toLong(data[16]))
        .tab((String) data[17])
        .ordenTab(toInt(data[18]))
        .build();
  }

  default List<LoginRolesMenuDTO> toDtoList(List<Object[]> rawData) {
    List<LoginRolesMenuDTO> result = new ArrayList<>();
    if (rawData != null) {
      for (Object[] row : rawData) {
        result.add(toDto(row));
      }
    }
    return result;
  }

  private static Long toLong(Object value) {
    return value != null ? ((Number) value).longValue() : null;
  }

  private static Integer toInt(Object value) {
    return value != null ? ((Number) value).intValue() : null;
  }

  private static UUID toUUID(Object value) {
      if (value == null) {
          return null;
      }

    if (value instanceof UUID uuid) {
      return uuid;
    }

    if (value instanceof byte[] bytes && bytes.length == 16) {
      ByteBuffer bb = ByteBuffer.wrap(bytes);
      long high = bb.getLong();
      long low = bb.getLong();
      return new UUID(high, low);
    }

    return UUID.fromString(value.toString());
  }
}