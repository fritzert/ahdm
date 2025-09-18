package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.MenuJpaEntity;

@Repository
public interface MenuRepository extends JpaRepository<MenuJpaEntity, Long> {

  @Query("""
      SELECT u FROM MenuEntity u
      WHERE (:nombre IS NULL OR UPPER(u.nombre) LIKE UPPER(CONCAT('%', :nombre, '%')))
      """)
  List<MenuJpaEntity> findByNombreLike(@Param("nombre") String nombre);

}