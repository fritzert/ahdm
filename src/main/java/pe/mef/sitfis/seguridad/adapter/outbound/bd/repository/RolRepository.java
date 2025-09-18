package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.RolJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.RolComboProjection;

@Repository
public interface RolRepository extends JpaRepository<RolJpaEntity, Long> {

  @Query("SELECT r.id AS id, r.nombre AS nombre FROM Rol r ORDER BY r.nombre ASC")
  List<RolComboProjection> findAllAsCombo();

  Page<RolJpaEntity> findAllByNombreContainingIgnoreCase(String nombre, Pageable pageable);

  Optional<RolJpaEntity> findByNombreIgnoreCase(String nombre);

  @Query("""
         SELECT r.id AS id, r.nombre AS nombre FROM Rol r
         WHERE NOT EXISTS (
             SELECT 1 FROM RolGrupo rg
             WHERE rg.roles.id = r.id
               AND rg.flagRestriccion = 1
               AND rg.grupos.id <> :grupoId
           )
         ORDER BY r.nombre ASC
      """)
  List<RolComboProjection> findAllAsComboRestriccion(@Param("grupoId") Long grupoId);

  @Modifying
  @Query("""
      UPDATE Rol r
      SET r.nombre = :nombre
      WHERE r.id = :id
      """)
  int updateNombreById(@Param("nombre") String nombre,
      @Param("id") Long id);

}
