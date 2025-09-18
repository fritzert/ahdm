package pe.mef.sitfis.seguridad.adapter.outbound.bd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "SubmenuEntity") // JPQL (Java)
@Table(name = "SUBMENU")
public class SubmenuJpaEntity {

  @Id
  @Column(name = "SUBMENU_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqSubMenu")
  @SequenceGenerator(sequenceName = "SUBMENU_SEQ", allocationSize = 1, name = "seqSubMenu")
  private Long id;

  @ManyToOne
  @JoinColumn(name = "MENU_ID", nullable = false, insertable = true, updatable = false)
  private MenuJpaEntity menuEntity;

  @Column(name = "NIVEL")
  private Integer nivel;

  @Column(name = "NOMBRE")
  private String nombre;

  @Column(name = "RUTA")
  private String ruta;

  @Column(name = "ORDEN")
  private Integer orden;
}
