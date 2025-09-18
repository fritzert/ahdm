package pe.mef.sitfis.seguridad.application.service;

import java.nio.ByteBuffer;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.GrupoRolMenuResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.MenuResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.RolComboResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.SubMenuResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.TabResponse;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoMenuResponse;
import pe.mef.sitfis.seguridad.application.exception.ResourceNotFoundException;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.UsuarioDatosLoginProjection;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.repository.LoginRolesMenuRepository;

@RequiredArgsConstructor
@Service
public class LoginRolesMenuServiceImpl implements LoginRolesMenuService {

  private final LoginRolesMenuRepository loginRolesMenuRepository;

  @Override
  public UsuarioRolGrupoMenuResponse obtenerMenusPorUsuario(String cuenta) {

    // 1. Obtiene directamente projections
    List<UsuarioDatosLoginProjection> datos = loginRolesMenuRepository.findDatosUsuarioLogin(
        cuenta);

    // 2. Mapa para agrupar por ID de grupo
    Map<String, GrupoRolMenuResponse> grupoMap = new LinkedHashMap<>();

    for (UsuarioDatosLoginProjection item : datos) {
      //UUID grupoId = item.getGrupoId();
      String usuarioRolGrupoId = item.getUsuarioRolGrupoId();

      GrupoRolMenuResponse grupoRolMenuResponse = grupoMap.computeIfAbsent(usuarioRolGrupoId,
          id -> new GrupoRolMenuResponse(
              hexToUUID(usuarioRolGrupoId),
              item.getGrupoId(),
              item.getNombreGrupo(),
              new LinkedHashSet<>(),
              new LinkedHashSet<>(),
              new LinkedHashSet<>(),
              new LinkedHashSet<>()
          ));

      if (item.getRolId() != null) {
        RolComboResponse rol = new RolComboResponse(item.getRolId(), item.getNombreRol());
        grupoRolMenuResponse.rol().add(rol);
      }

      if (item.getMenuId() != null) {
        MenuResponse menuResponse = new MenuResponse(item.getMenuId(), item.getNombreMenu(), item.getRutaMenu(),
            item.getOrdenMenu());
        grupoRolMenuResponse.menu().add(menuResponse);
      }

      if (item.getSubmenuId() != null) {
        SubMenuResponse subMenuResponse = new SubMenuResponse(item.getMenuId(), item.getSubmenuId(), item.getNombreSubmenu(), item.getRutaSubmenu(), item.getNivelSubmenu(), 
        		item.getOrdenMenu());
        grupoRolMenuResponse.submenu().add(subMenuResponse);
      }

      if (item.getTabId() != null) {
        TabResponse tabResponse = new TabResponse(item.getMenuId(),item.getSubmenuId(), item.getTabId(), item.getNombreTab(), item.getComponenteTab(),
            item.getOrdenTab());
        grupoRolMenuResponse.tab().add(tabResponse);
      }
    }

    if (datos.isEmpty()) {
      throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.toString());
    }

    // 3. Armar record final
    UsuarioDatosLoginProjection first = datos.get(0);
    return new UsuarioRolGrupoMenuResponse(
        hexToUUID(first.getUsuarioId()),
        hexToUUID(first.getPersonaId()),
        first.getCuenta(),
        first.getGrupoPrincipalId(),
        new LinkedHashSet<>(grupoMap.values())
    );
  }

  /*public static UUID hexToUUID(String rawHex) {
		if (rawHex == null) {
			return null;
		}
    return UUID.fromString(rawHex.replaceFirst(
        "(\\w{8})(\\w{4})(\\w{4})(\\w{4})(\\w{12})", "$1-$2-$3-$4-$5"
    ));
  }*/
  public static UUID hexToUUID(String hex) {
	    if (hex == null) {
	        return null;
	    }
	    if (hex.contains("-")) {
	        // ya es UUID
	        return UUID.fromString(hex);
	    }
	    if (hex.length() != 32) {
	        throw new IllegalArgumentException("Cadena no válida para UUID: " + hex);
	    }

	    byte[] bytes = new byte[16];
	    for (int i = 0; i < 16; i++) {
	        bytes[i] = (byte) Integer.parseInt(hex.substring(i * 2, i * 2 + 2), 16);
	    }

	    ByteBuffer bb = ByteBuffer.wrap(bytes);
	    long high = bb.getLong();
	    long low = bb.getLong();
	    return new UUID(high, low);
	}
}
