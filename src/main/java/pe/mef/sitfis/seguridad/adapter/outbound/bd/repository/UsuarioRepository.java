package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioEntity;

@Repository
public interface UsuarioRepository extends JpaRepository<UsuarioEntity, UUID> {

  @Query("""
      SELECT u FROM UsuarioEntity u
      WHERE (:cuenta IS NULL OR UPPER(u.cuenta) LIKE :cuenta)
      AND (:estado IS NULL OR u.estado = :estado)
      """)
  Page<UsuarioEntity> findbyCuentaFiltro(
      @Param("cuenta") String cuenta,
      @Param("estado") int estado,
      Pageable pageable
  );

  @Query("""
      SELECT u FROM UsuarioEntity u
      WHERE (:cuenta IS NULL OR UPPER(u.cuenta) LIKE :cuenta)
      AND (:estado IS NULL OR u.estado = :estado)
      AND u.personaId IN (:idpersona)
      """)
  Page<UsuarioEntity> findbyUsuarioPersonaFiltro(
      @Param("cuenta") String cuenta,
      @Param("estado") int estado,
      @Param("idpersona") List<UUID> idpersona,
      Pageable pageable
  );

  @Query("""
      SELECT u FROM UsuarioEntity u
      WHERE ( (:cuenta IS NULL OR UPPER(u.cuenta) LIKE :cuenta)
                OR u.id IN (:idpersona)
            )
      AND (u.estado = 1)
      AND u.rolGrupos IS EMPTY
      """)
  List<UsuarioEntity> findbylikeUsuarioConIDPersonaSinRoles(
      @Param("cuenta") String cuenta,
      @Param("idpersona") List<UUID> idpersona
  );

  @Query("""
      SELECT u FROM UsuarioEntity u
      WHERE (:cuenta IS NULL OR UPPER(u.cuenta) LIKE :cuenta)
      AND (u.estado = 1)
      AND u.rolGrupos IS EMPTY
      """)
  List<UsuarioEntity> findbylikeUsuarioSinIDPersonasSinRoles(
      @Param("cuenta") String cuenta
  );

  @Query("""
      SELECT u FROM UsuarioEntity u
      WHERE (:cuenta IS NULL OR UPPER(u.cuenta) LIKE :cuenta)
      AND (u.estado = 1)
      AND u.rolGrupos IS NOT EMPTY
      """)
  List<UsuarioEntity> findbylikeUsuarioSinIDPersonaConRoles(
      @Param("cuenta") String cuenta
  );

  @Query("""
      SELECT u FROM UsuarioEntity u
      WHERE (
             (:cuenta IS NULL OR UPPER(u.cuenta) LIKE :cuenta)
             OR u.id IN (:idpersona)
            )
      AND (u.estado = 1)
      AND u.rolGrupos IS NOT EMPTY
      """)
  List<UsuarioEntity> findbylikeUsuarioConIDPersonaConRoles(
      @Param("cuenta") String cuenta,
      @Param("idpersona") List<UUID> idpersona
  );
  
  @Query("select p.personaId from UsuarioEntity p where upper(p.cuenta)=upper(:cuenta) and p.estado =1")
  UUID obtenerPersonaId(@Param("cuenta") String cuenta);

  @Query("select p from UsuarioEntity p where upper(p.cuenta)=upper(:cuenta) and p.estado =1")
  Optional<UsuarioEntity> findByUsuario(@Param("cuenta") String cuenta);

  @Query("select u from UsuarioEntity u")
  Page<UsuarioEntity> findAllPaginacion(Pageable pageable);

  @Query("""
      SELECT u from UsuarioEntity u
      WHERE (:cuenta IS NULL OR UPPER(u.cuenta) LIKE :cuenta)
      """)
  Page<UsuarioEntity> findAllPaginacionFiltroCuenta(Pageable pageable,
      @Param("cuenta") String cuenta);

  @Query("""
      SELECT u from UsuarioEntity u
      WHERE (:cuenta IS NULL OR UPPER(u.cuenta) LIKE :cuenta)
      AND u.personaId IN (:idpersona)
      """)
  Page<UsuarioEntity> findAllPaginacionFiltroCuentaPersonId(Pageable pageable,
      @Param("cuenta") String cuenta, @Param("idpersona") List<UUID> idpersona);


  @Modifying
  @Query("update UsuarioEntity set grupoPrincipalId=:grupoidprincipal where id =:id")
  int upd_grupoIDPrincipal(@Param("id") UUID id, @Param("grupoidprincipal") Long grupoidprincipal);
}
