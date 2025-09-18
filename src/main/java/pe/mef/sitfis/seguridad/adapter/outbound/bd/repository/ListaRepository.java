package pe.mef.sitfis.seguridad.adapter.outbound.bd.repository;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pe.mef.sitfis.seguridad.adapter.outbound.bd.entity.ListaJpaEntity;

@Repository
public interface ListaRepository extends JpaRepository<ListaJpaEntity, UUID> {

}