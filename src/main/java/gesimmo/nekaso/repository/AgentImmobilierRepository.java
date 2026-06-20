package gesimmo.nekaso.repository;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gesimmo.nekaso.entity.AgentImmobilier;

@Repository
public interface AgentImmobilierRepository extends JpaRepository<AgentImmobilier, Long> {
    Optional<AgentImmobilier> findById(Long id);
    Optional<AgentImmobilier> findByTelephone(String telephone);
    Page<AgentImmobilier> findByGestionnaire_Id(Long idGestionnaire, Pageable pageable);
}
