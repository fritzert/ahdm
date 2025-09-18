package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.SubmenuJpaEntity;

@Repository
public interface SubMenuRepository extends JpaRepository<SubmenuJpaEntity, Long> {

  @Query("""
      SELECT u FROM SubmenuEntity u
      WHERE (:nombre IS NULL OR UPPER(u.nombre) LIKE :nombre)
      AND u.menuEntity.id = :menuId
      """)
  List<SubmenuJpaEntity> findAllSubMenuByMenuId(@Param("nombre") String nombre,
      @Param("menuId") Long menuId);

}