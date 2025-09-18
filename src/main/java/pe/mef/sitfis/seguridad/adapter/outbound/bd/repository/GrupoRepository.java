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
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.GrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.GrupoComboProjection;

@Repository
public interface GrupoRepository extends JpaRepository<GrupoJpaEntity, Long> {

  @Query("SELECT g.id AS id, g.nombre AS nombre FROM Grupo g ORDER BY g.nombre ASC")
  List<GrupoComboProjection> findAllAsCombo();

  @Query("""
          SELECT DISTINCT g FROM Grupo g
            WHERE (
                (:grupoId IS NULL AND :rolId IS NULL AND :nombre IS NULL)
                OR
                (:grupoId IS NULL AND :rolId IS NULL AND :nombre IS NOT NULL AND UPPER(g.nombre) LIKE UPPER(CONCAT('%', :nombre, '%')))
                OR
                (:grupoId IS NULL AND :rolId IS NOT NULL AND g.id IN (
                    SELECT rg.grupos.id FROM RolGrupo rg
                    WHERE rg.roles.id = :rolId
                ) AND (:nombre IS NULL OR UPPER(g.nombre) LIKE UPPER(CONCAT('%', :nombre, '%'))))
                OR
                (:grupoId IS NOT NULL AND :rolId IS NULL AND g.id IN (
                    SELECT rg.grupos.id FROM RolGrupo rg
                    WHERE rg.grupos.id = :grupoId
                ) AND (:nombre IS NULL OR UPPER(g.nombre) LIKE UPPER(CONCAT('%', :nombre, '%'))))
                OR
                (:grupoId IS NOT NULL AND :rolId IS NOT NULL AND g.id IN (
                    SELECT rg.grupos.id FROM RolGrupo rg
                    WHERE rg.roles.id = :rolId AND rg.grupos.id = :grupoId
                ) AND (:nombre IS NULL OR UPPER(g.nombre) LIKE UPPER(CONCAT('%', :nombre, '%'))))
                OR
                (:grupoId IS NOT NULL AND g.id = :grupoId)
            )
      """)
  Page<GrupoJpaEntity> findByNombreGrupoIdRolIdPaginado(
      @Param("nombre") String nombre, @Param("grupoId") Long grupoId, @Param("rolId") Long rolId,
      Pageable pageable);

  Optional<GrupoJpaEntity> findByNombreIgnoreCaseOrCodigoIgnoreCase(String nombre, String codigo);

  
  @Query("""
      SELECT e FROM Grupo e
      WHERE (LOWER(e.nombre) = LOWER(:nombre)
      OR LOWER(e.codigo) = LOWER(:codigo)) 
      AND  id <> :id
      """)
  List<GrupoJpaEntity> findByNombreCodigoIgnorandoId(@Param("nombre") String nombre, 
		  @Param("codigo") String codigo, 
		  @Param("id") Long id);

  @Modifying
  @Query("""
      UPDATE Grupo g
      SET g.nombre = :nombre,
          g.codigo = :codigo
      WHERE g.id = :id
      """)
  int updateNombreCodigoById(@Param("nombre") String nombre,
      @Param("codigo") String codigo,
      @Param("id") Long id);

}
