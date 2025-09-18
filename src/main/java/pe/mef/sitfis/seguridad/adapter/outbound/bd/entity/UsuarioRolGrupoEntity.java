package pe.mef.sitfis.seguridad.adapter.outbound.bd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import pe.mef.sitfis.seguridad.adapter.config.auditoria.AuditJpaEntity;

@Data
@Entity(name = "UsuarioRolGrupoEntity") // JPQL (Java)
@Table(name = "USUARIO_ROL_GRUPO")
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
public class UsuarioRolGrupoEntity extends AuditJpaEntity {

  @Id
  @GeneratedValue
  @Column(name = "USUARIO_ROL_GRUPO_ID", columnDefinition = "RAW(16)")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "USUARIO_ID")
  private UsuarioEntity usuario;

  @ManyToOne
  @JoinColumn(name = "ROL_GRUPO_ID")
  private RolGrupoJpaEntity rolGrupo;
}
