package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.UsuarioDatosLoginProjection;

@Repository
public interface LoginRolesMenuRepository extends JpaRepository<UsuarioEntity, UUID> {

  @Query(value = """
      SELECT 
          RAWTOHEX(USU.USUARIO_ID) AS usuarioId,
          USU.CUENTA AS cuenta,
          RAWTOHEX(USU.PERSONA_ID) AS personaId,
          USU.GRUPO_PRINCIPAL_ID AS grupoPrincipalId,
          RAWTOHEX(URG.USUARIO_ROL_GRUPO_ID) as usuarioRolGrupoId,
          URG.ROL_GRUPO_ID AS rolGrupoId,
          G.GRUPO_ID AS grupoId,
          G.NOMBRE AS nombreGrupo,
          R.ROL_ID AS rolId,
          R.NOMBRE AS nombreRol,
          M.MENU_ID AS menuId,
          M.NOMBRE AS nombreMenu,
          M.RUTA AS rutaMenu,
          M.ORDEN AS ordenMenu,
          SUB.SUBMENU_ID AS submenuId,
          SUB.NOMBRE AS nombreSubmenu,
          SUB.RUTA AS rutaSubmenu,
          SUB.NIVEL AS nivelSubmenu,
          SUB.ORDEN AS ordenSubmenu,
          T.TAB_ID AS tabId,
          T.NOMBRE AS nombreTab,
          T.COMPONENTE AS componenteTab,
          T.ORDEN AS ordenTab
      FROM SITFISSEGURIDAD.USUARIO USU
      INNER JOIN SITFISSEGURIDAD.USUARIO_ROL_GRUPO URG ON USU.USUARIO_ID = URG.USUARIO_ID
      INNER JOIN SITFISSEGURIDAD.ROL_GRUPO RG ON URG.ROL_GRUPO_ID = RG.ROL_GRUPO_ID
      INNER JOIN SITFISSEGURIDAD.ROL R ON RG.ROL_ID = R.ROL_ID
      INNER JOIN SITFISSEGURIDAD.GRUPO G ON RG.GRUPO_ID = G.GRUPO_ID
      INNER JOIN SITFISSEGURIDAD.LISTA_ROL_GRUPO LRG ON RG.ROL_GRUPO_ID = LRG.ROL_GRUPO_ID
      INNER JOIN SITFISSEGURIDAD.LISTA L ON LRG.LISTA_ID = L.LISTA_ID
      INNER JOIN SITFISSEGURIDAD.TAB T ON L.TAB_ID = T.TAB_ID AND L.SUBMENU_ID = T.SUBMENU_ID AND L.MENU_ID = T.MENU_ID
      INNER JOIN SITFISSEGURIDAD.SUBMENU SUB ON T.SUBMENU_ID = SUB.SUBMENU_ID
      INNER JOIN SITFISSEGURIDAD.MENU M ON SUB.MENU_ID = M.MENU_ID
      WHERE USU.CUENTA = :cuenta
      ORDER BY G.GRUPO_ID, M.ORDEN, SUB.NIVEL, SUB.ORDEN, T.ORDEN
      """, nativeQuery = true)
  List<UsuarioDatosLoginProjection> findDatosUsuarioLogin(@Param("cuenta") String cuenta);
}