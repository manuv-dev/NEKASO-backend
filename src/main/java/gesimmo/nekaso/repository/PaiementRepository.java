package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PaiementRepository extends JpaRepository<Paiement, Long> {
    // @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.contrat.demandeLocation.bienImmobilier.gestionnaire.id = :gestionnaireId AND FUNCTION('TO_CHAR', p.datePaiement, 'YYYY-MM') = :mois")
    // Double sumMontantByGestionnaireAndMonth(Long gestionnaireId, String mois);

    // @Query("SELECT SUM(p.montant) FROM Paiement p WHERE p.contrat.demandeLocation.bienImmobilier.gestionnaire.id = :gestionnaireId AND p.datePaiement IS NULL")
    // Double sumMontantRetardByGestionnaire(Long gestionnaireId);

    // @Query("SELECT COUNT(DISTINCT p.contrat.demandeLocation.locataire.id) FROM Paiement p WHERE p.contrat.demandeLocation.bien.gestionnaire.id = :gestionnaireId AND p.datePaiement IS NULL")
    // int countLocatairesRetardByGestionnaire(Long gestionnaireId);
}
