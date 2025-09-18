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
import pe.mef.sitfis.seguridad.adapter.config.auditoria.AuditJpaEntity;

@Data
@Entity
@Table(name = "LISTA_ROL_GRUPO")
@EqualsAndHashCode(callSuper = true)
public class ListaRolGrupoJpaEntity extends AuditJpaEntity {

  @Id
  @GeneratedValue
  @Column(name = "LISTA_ROL_GRUPO_ID", columnDefinition = "RAW(16)")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "LISTA_ID")
  private ListaJpaEntity lista;

  @ManyToOne
  @JoinColumn(name = "ROL_GRUPO_ID")
  private RolGrupoJpaEntity rolGrupo;

}
