package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.TabJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.TabMenuSubmenuProjection;

@Repository
public interface TabRepository extends JpaRepository<TabJpaEntity, Long> {

  @Query("""
          SELECT u FROM TabEntity u
          WHERE (:nombre IS NULL OR UPPER(u.nombre) LIKE :nombre)
            AND u.menu.id = :menuId
            AND u.submenu.id = :submenuId
      """)
  List<TabJpaEntity> findAllByNombreMenuSubmenu(
      @Param("nombre") String nombre,
      @Param("submenuId") Long submenuId,
      @Param("menuId") Long menuId
  );

  @Query("""
          SELECT
            t.id AS tabId,
            t.nombre AS tabNombre,
            t.componente AS componente,
            t.orden AS orden,
            t.menu.id AS menuId,
            t.menu.nombre AS menuNombre,
            t.submenu.id AS submenuId,
            t.submenu.nombre AS submenuNombre
          FROM TabEntity t
          WHERE (:tabId IS NULL OR t.id = :tabId)
            AND (:menuId IS NULL OR t.menu.id = :menuId)
            AND (:submenuId IS NULL OR t.submenu.id = :submenuId)
      """)
  Page<TabMenuSubmenuProjection> findByMenuSubmenuTabPaginado(
      @Param("menuId") Long menuId,
      @Param("submenuId") Long submenuId,
      @Param("tabId") Long tabId,
      Pageable pageable);

}
