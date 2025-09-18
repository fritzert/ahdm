package pe.mef.sitfis.seguridad.adapter.outbound.bd.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity(name = "MenuEntity") // JPQL (Java)
@Table(name = "MENU")
public class MenuJpaEntity {

  @Id
  @Column(name = "MENU_ID")
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seqMenu")
  @SequenceGenerator(sequenceName = "MENU_SEQ", allocationSize = 1, name = "seqMenu")
  private Long id;

  @Column(name = "NOMBRE")
  private String nombre;
  
  @Column(name = "RUTA")
  private String ruta;

  @Column(name = "ORDEN")
  private Integer orden;

}
