package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.Quittance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface QuittanceRepository extends JpaRepository<Quittance, Long> {
    Optional<Quittance> findByPaiementId(Long paiementId);
}
