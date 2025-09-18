package pe.mef.sitfis.seguridad.adapter.outbound.bd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.mef.sitfis.seguridad.adapter.config.auditoria.AuditJpaEntity;

@Data
@Entity(name = "Rol")
@Table(name = "ROL")
@EqualsAndHashCode(callSuper = true)
public class RolJpaEntity extends AuditJpaEntity {

  @Id
  @Column(name = "ROL_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRol")
  @SequenceGenerator(sequenceName = "ROL_SEQ", allocationSize = 1, name = "seqRol")
  private Long id;

  @Column(name = "NOMBRE")
  private String nombre;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "roles", orphanRemoval = true)
  private Set<RolGrupoJpaEntity> rolGrupo = new HashSet<>();

}
