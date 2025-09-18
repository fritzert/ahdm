package pe.mef.sitfis.seguridad.adapter.outbound.bd.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import pe.mef.sitfis.seguridad.adapter.config.auditoria.AuditJpaEntity;

@Data
@Entity(name = "UsuarioEntity") // JPQL (Java)
@Table(name = "USUARIO")        // SQL(PL/SQL)
@DynamicInsert
@DynamicUpdate
@EqualsAndHashCode(callSuper = true)
public class UsuarioEntity extends AuditJpaEntity {

  @Id
  @GeneratedValue
  @Column(name = "USUARIO_ID", columnDefinition = "RAW(16)")
  private UUID id;

  @Column(name = "PERSONA_ID")
  private UUID personaId;

  @Column(name = "CUENTA")
  private String cuenta;

  @Column(name = "CLAVE")
  private String clave;

  @Column(name = "GRUPO_PRINCIPAL_ID")
  private Long grupoPrincipalId;
  
  @Column(name = "INTENTO_FALLIDO")
  private int intentoFallido;
  
  @Column(name = "FECHA_CADUCIDAD")
  private LocalDate fechaCaducidad;

  @Column(name = "ESTADO")
  private int estado;

  @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @JoinTable(name = "USUARIO_ROL_GRUPO", joinColumns = {
      @JoinColumn(name = "USUARIO_ID")}, inverseJoinColumns = {
      @JoinColumn(name = "ROL_GRUPO_ID")})
  private Set<RolGrupoJpaEntity> rolGrupos = new HashSet<>();
}
