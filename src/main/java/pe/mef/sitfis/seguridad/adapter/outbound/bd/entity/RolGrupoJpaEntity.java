package pe.mef.sitfis.seguridad.adapter.outbound.bd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.mef.sitfis.seguridad.adapter.config.auditoria.AuditJpaEntity;

@Data
@Entity(name = "RolGrupo")
@Table(name = "ROL_GRUPO")
@EqualsAndHashCode(callSuper = true)
public class RolGrupoJpaEntity extends AuditJpaEntity {

  @Id
  @Column(name = "ROL_GRUPO_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqRolGrupo")
  @SequenceGenerator(sequenceName = "ROL_GRUPO_SEQ", allocationSize = 1, name = "seqRolGrupo")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "ROL_ID")
  private RolJpaEntity roles;

  @ManyToOne
  @JoinColumn(name = "GRUPO_ID")
  private GrupoJpaEntity grupos;

  @Column(name = "FLAG_RESTRICCION")
  private int flagRestriccion;

  @Column(name = "FLAG_CONSULTA")
  private int flagConsulta;

  @Column(name = "FLAG_OPERACION")
  private int flagOperacion;

  @Column(name = "FLAG_ASIGNAR_RECURSOS")
  private int flagAsignarRecursos;

  @Column(name = "FLAG_ENVIAR_BANDEJA")
  private int flagEnviarBandeja;

  @Column(name = "FLAG_ENVIAR_ETAPA")
  private int flagEnviarEtapa;

  @Column(name = "FLAG_ADJUNTAR_ARCHIVO")
  private int flagAdjuntarArchivo;

  //    @Transient
  @OneToMany(cascade = CascadeType.ALL, mappedBy = "rolGrupo", orphanRemoval = true)
  private Set<ListaRolGrupoJpaEntity> listaRolGrupo = new HashSet<>();

}
