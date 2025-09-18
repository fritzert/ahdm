package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.ListaRolGrupoJpaEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.ListaRolGrupoMenuProjection;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.projection.ListaRolGrupoProjection;

@Repository
public interface ListaRolGrupoRepository extends JpaRepository<ListaRolGrupoJpaEntity, UUID> {

  @Query("""
          SELECT
              l.id AS id,
              l.rolGrupo.id AS rolGrupoId,
              r.roles.id AS rolId,
              r.roles.nombre AS rolNombre,
              r.grupos.id AS grupoId,
              r.grupos.nombre AS grupoNombre,
              l.usuarioModificacion AS usuarioModificacion,
              l.fechaModificacion AS fechaModificacion
          FROM ListaRolGrupoJpaEntity l
          JOIN l.rolGrupo r
          ORDER BY fechaModificacion desc
      """)
  List<ListaRolGrupoProjection> findAllReporte();

  @Query("""
          SELECT
              l.id AS id,
              l.rolGrupo.id AS rolGrupoId,
              r.roles.id AS rolId,
              r.roles.nombre AS rolNombre,
              r.grupos.id AS grupoId,
              r.grupos.nombre AS grupoNombre,
              l.fechaModificacion AS fechaModificacion
          FROM ListaRolGrupoJpaEntity l
          JOIN l.rolGrupo r
          WHERE (:rolId IS NULL OR r.roles.id = :rolId)
            AND (:grupoId IS NULL OR r.grupos.id = :grupoId)
          ORDER BY rolGrupoId, grupoId, rolId
      """)
  Page<ListaRolGrupoProjection> findListaByRolGrupoPaginado(
      @Param("grupoId") Long grupoId, @Param("rolId") Long rolId, Pageable pageable);

  @Query("""
          SELECT
              lrg.id AS id,
              rg.id AS rolGrupoId,
              l.id AS listaId,
              l.menu.id AS menuId,
              l.menu.nombre AS menuNombre,
              l.submenu.id AS submenuId,
              l.submenu.nombre AS submenuNombre,
              l.tab.id AS tabId,
              l.tab.nombre AS tabNombre,
              lrg.fechaModificacion AS fechaModificacion
          FROM ListaRolGrupoJpaEntity lrg
          JOIN lrg.lista l
          JOIN lrg.rolGrupo rg
          WHERE (:rolId IS NULL OR rg.roles.id = :rolId)
            AND (:grupoId IS NULL OR rg.grupos.id = :grupoId)
          ORDER BY fechaModificacion DESC
      """)
  List<ListaRolGrupoMenuProjection> findListaMenuByRolGrupo(
      @Param("grupoId") Long grupoId, @Param("rolId") Long rolId);

  @Query("""
          SELECT CASE WHEN COUNT(lrg) > 0 THEN TRUE ELSE FALSE END
          FROM ListaRolGrupoJpaEntity lrg
          JOIN lrg.lista l
          JOIN l.menu m
          JOIN l.submenu sm
          JOIN l.tab t
          WHERE (:menuId IS NULL OR m.id = :menuId)
            AND (:submenuId IS NULL OR sm.id = :submenuId)
            AND (:tabId IS NULL OR t.id = :tabId)
            AND (:rolGrupoId IS NULL OR lrg.rolGrupo.id = :rolGrupoId)
      """)
  boolean existsByMenuSubmenuTabRolGrupo(
      @Param("menuId") Long menuId,
      @Param("submenuId") Long submenuId,
      @Param("tabId") Long tabId,
      @Param("rolGrupoId") Long rolGrupoId);

}
