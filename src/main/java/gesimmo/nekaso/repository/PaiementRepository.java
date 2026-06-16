package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaiementRepository extends JpaRepository<Paiement, Long> {
   Paiement save(Paiement paiement);
   Page<Paiement> findByContratId(Long id, Pageable pageable);
}
