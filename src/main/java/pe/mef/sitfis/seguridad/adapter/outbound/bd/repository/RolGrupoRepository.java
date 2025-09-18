package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.RolGrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.RolGrupoProjection;
import pe.mef.sitfis.seguridad.application.command.CrearRolGrupoCommand;

@Repository
public interface RolGrupoRepository extends JpaRepository<RolGrupoJpaEntity, Long> {

  @Query("""
          SELECT rg.id AS id, rg.roles.id AS rolId, rg.roles.nombre AS rolNombre,
          rg.grupos.id AS grupoId,
          rg.flagRestriccion AS flagRestriccion,
          rg.flagConsulta AS flagConsulta,
          rg.flagOperacion AS flagOperacion,
          rg.flagAsignarRecursos AS flagAsignarRecursos,
          rg.flagEnviarBandeja AS flagEnviarBandeja,
          rg.flagEnviarEtapa AS flagEnviarEtapa,
          rg.flagAdjuntarArchivo AS flagAdjuntarArchivo,
          rg.usuarioModificacion AS usuarioModificacion,
          rg.fechaModificacion AS fechaModificacion
          FROM RolGrupo rg
          WHERE rg.grupos.id = :grupoId
          ORDER BY rg.roles.nombre ASC
      """)
  List<RolGrupoProjection> findAllByGrupoId(@Param("grupoId") Long grupoId);

  @Query("SELECT rg FROM RolGrupo rg WHERE rg.grupos.id = :grupoId")
  List<RolGrupoJpaEntity> findByGrupoId(@Param("grupoId") Long grupoId);

  @Query("SELECT rg FROM RolGrupo rg WHERE rg.roles.id = :rolId")
  List<RolGrupoJpaEntity> findByRolId(@Param("rolId") Long rolId);

  @Query("SELECT rg FROM RolGrupo rg WHERE rg.roles.id = :rolId AND rg.grupos.id = :grupoId")
  Optional<RolGrupoJpaEntity> findByRolIdAndGrupoId(
      @Param("rolId") Long rolId,
      @Param("grupoId") Long grupoId);

  @Modifying
  @Query("""
          UPDATE RolGrupo rg SET
          rg.roles.id = :#{#command.rolId},
          rg.grupos.id = :#{#command.grupoId},
          rg.flagRestriccion = :#{#command.flagRestriccion},
          rg.flagConsulta = :#{#command.flagConsulta},
          rg.flagOperacion = :#{#command.flagOperacion},
          rg.flagAsignarRecursos = :#{#command.flagAsignarRecursos},
          rg.flagEnviarBandeja = :#{#command.flagEnviarBandeja},
          rg.flagEnviarEtapa = :#{#command.flagEnviarEtapa},
          rg.flagAdjuntarArchivo = :#{#command.flagAdjuntarArchivo}
          WHERE rg.id = :#{#command.id}
      """)
  void actualizarRolGrupoConRecord(@Param("command") CrearRolGrupoCommand command);

  @Query("SELECT case when (count(lrg) > 0) then true else false end FROM ListaRolGrupoJpaEntity lrg WHERE lrg.rolGrupo.id = :id")
  boolean existsRelatedEntities(@Param("id") Long id);

  @Modifying
  @Query("""
      UPDATE RolGrupo rg SET
      rg.roles.id = :rolId,
      rg.grupos.id = :grupoId,
      rg.flagRestriccion = :flagRestriccion,
      rg.flagConsulta = :flagConsulta,
      rg.flagOperacion = :flagOperacion,
      rg.flagAsignarRecursos = :flagAsignarRecursos,
      rg.flagEnviarBandeja = :flagEnviarBandeja,
      rg.flagEnviarEtapa = :flagEnviarEtapa,
      rg.flagAdjuntarArchivo = :flagAdjuntarArchivo
      WHERE rg.id = :id
      """)
  int actualizarRolGrupo(
      @Param("id") Long id,
      @Param("rolId") Long rolId,
      @Param("grupoId") Long grupoId,
      @Param("flagRestriccion") int flagRestriccion,
      @Param("flagConsulta") int flagConsulta,
      @Param("flagOperacion") int flagOperacion,
      @Param("flagAsignarRecursos") int flagAsignarRecursos,
      @Param("flagEnviarBandeja") int flagEnviarBandeja,
      @Param("flagEnviarEtapa") int flagEnviarEtapa,
      @Param("flagAdjuntarArchivo") int flagAdjuntarArchivo
  );

//  @Query("SELECT rg FROM RolGrupo rg WHERE rg.roles.id = :rolId AND rg.grupos.id = :grupoId")
//  Optional<RolGrupoJpaEntity> findByRolIdAndGrupoId(
//      @Param("rolId") Long rolId,
//      @Param("grupoId") Long grupoId);
//
//  // Para validaciones de eliminación
//  @Query("SELECT COUNT(urg) FROM UsuarioRolGrupoEntity urg WHERE urg.rolGrupo.id = :rolGrupoId")
//  long countUsuariosByRolGrupoId(@Param("rolGrupoId") Long rolGrupoId);
//
//  @Query("SELECT COUNT(lrg) FROM ListaRolGrupoJpaEntity lrg WHERE lrg.rolGrupo.id = :rolGrupoId")
//  long countListasByRolGrupoId(@Param("rolGrupoId") Long rolGrupoId);
}
