package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.Quittance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface QuittanceRepository extends JpaRepository<Quittance, Long> {
    // Optional<Quittance> findByPaiement_Id(Long paiementId);

    // @Query("SELECT q FROM Quittance q WHERE q.paiement.locataire.id = :locataireId")
    // List<Quittance> findByLocataire(@Param("locataireId") Long locataireId);

    // // @Query("SELECT q FROM Quittance q WHERE q.paiement.locataire.id = :locataireId AND q.paiement.bien.id = :bienId")
    // // List<Quittance> findByLocataireAndBien(@Param("locataireId") Long locataireId, @Param("bienId") Long bienId);

    // @Query("SELECT q FROM Quittance q WHERE q.paiement.bien.id = :bienId")
    // List<Quittance> findByBien(@Param("bienId") Long bienId);

    // @Query("SELECT q FROM Quittance q WHERE q.paiement.bien.id = :bienId AND q.paiement.locataire.id = :locataireId")
    // List<Quittance> findByBienAndLocataire(@Param("bienId") Long bienId, @Param("locataireId") Long locataireId);
}
