package pe.mef.sitfis.seguridad.adapter.config.auditoria;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class AuditJpaEntity {

  @Column(name = "FECHA_CREACION", nullable = false, updatable = false)
  @CreatedDate
  private LocalDateTime fechaCreacion;

  @Column(name = "FECHA_MODIFICACION", insertable = false)
  @LastModifiedDate
  private LocalDateTime fechaModificacion;

  @Column(name = "USUARIO_CREACION", nullable = false, updatable = false)
  @CreatedBy
  private String usuarioCreacion;

  @Column(name = "USUARIO_MODIFICACION", insertable = true)
  @LastModifiedBy
  private String usuarioModificacion;

}
