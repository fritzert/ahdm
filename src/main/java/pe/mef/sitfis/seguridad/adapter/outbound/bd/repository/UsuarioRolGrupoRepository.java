package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.inbound.api.dto.UsuarioRolGrupoRequest;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioEntity;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.UsuarioRolGrupoEntity;

@Repository
public interface UsuarioRolGrupoRepository extends JpaRepository<UsuarioRolGrupoEntity, UUID> {

  @Query("""
          SELECT p FROM UsuarioRolGrupoEntity p
          WHERE  p.usuario.id IN (:idsUsuarios)
      """)
  List<UsuarioRolGrupoEntity> findByIdsUsuarios(@Param("idsUsuarios") List<UUID> idsUsuarios);

  @Modifying
  @Query(value = """
          INSERT INTO USUARIO_ROL_GRUPO (USUARIO_ID, ROL_GRUPO_ID, USUARIO_CREACION, USUARIO_MODIFICACION)
          SELECT :idusuarioPorCopiar, rg.ROL_GRUPO_ID, :usuarioActual, :usuarioActual
          FROM USUARIO_ROL_GRUPO rg
          WHERE rg.USUARIO_ID = :idusuarioCopiado
      """, nativeQuery = true)
  void copiarUsuarioRolGrupo(
      @Param("idusuarioPorCopiar") UUID idusuarioPorCopiar,
      @Param("idusuarioCopiado") UUID idusuarioCopiado,
      @Param("usuarioActual") String usuarioActual
  );


  List<UsuarioRolGrupoEntity> findByUsuario(UsuarioEntity usuario);


  @Modifying
  @Query("UPDATE UsuarioRolGrupoEntity rg SET " +
      "rg.usuario.id = :#{#request.usuarioId.id}, " +
      "rg.rolGrupo.id = :#{#request.rolGrupoId.id} " +
      "WHERE rg.id = :#{#request.id}")
  void actualizarUsuarioRolGrupoConRecord(@Param("request") UsuarioRolGrupoRequest request);

  @Query("""
          SELECT p FROM UsuarioRolGrupoEntity p
          WHERE p.id IN :ids
      """)
  List<UsuarioRolGrupoEntity> findByIds(@Param("ids") List<UUID> ids);

  @Modifying
  @Query("""
          DELETE FROM UsuarioRolGrupoEntity p
          WHERE p.id IN :ids
      """)
  void eliminarMultipleUsuarioRolGrupo(@Param("ids") List<UUID> ids);
}
