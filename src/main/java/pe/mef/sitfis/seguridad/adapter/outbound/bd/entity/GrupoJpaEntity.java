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
@Entity(name = "Grupo")
@Table(name = "GRUPO")
@EqualsAndHashCode(callSuper = true)
public class GrupoJpaEntity extends AuditJpaEntity {

  @Id
  @Column(name = "GRUPO_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqGrupo")
  @SequenceGenerator(sequenceName = "GRUPO_SEQ", allocationSize = 1, name = "seqGrupo")
  private Long id;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "CODIGO")
  private String codigo;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "grupos", orphanRemoval = true)
  private Set<RolGrupoJpaEntity> rolGrupo = new HashSet<>();

}
