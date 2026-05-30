package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface PaiementRepository extends JpaRepository<Paiement, Long> {
}
