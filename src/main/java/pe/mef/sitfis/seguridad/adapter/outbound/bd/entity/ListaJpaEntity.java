package pe.mef.sitfis.seguridad.adapter.outbound.bd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import pe.mef.sitfis.seguridad.adapter.config.auditoria.AuditJpaEntity;

@Data
@Entity
@Table(name = "LISTA")
@EqualsAndHashCode(callSuper = true)
public class ListaJpaEntity extends AuditJpaEntity {

  @Id
  @GeneratedValue
  @Column(name = "LISTA_ID", columnDefinition = "RAW(16)")
  private UUID id;

  @ManyToOne
  @JoinColumn(name = "TAB_ID", nullable = false, insertable = true, updatable = false)
  private TabJpaEntity tab;

  @ManyToOne
  @JoinColumn(name = "SUBMENU_ID", nullable = false, insertable = true, updatable = false)
  private SubmenuJpaEntity submenu;

  @ManyToOne
  @JoinColumn(name = "MENU_ID", nullable = false, insertable = true, updatable = false)
  private MenuJpaEntity menu;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "lista", orphanRemoval = true)
  private Set<ListaRolGrupoJpaEntity> listaRolGrupo = new HashSet<ListaRolGrupoJpaEntity>();

}
