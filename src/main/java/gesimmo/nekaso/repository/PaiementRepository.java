package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.*;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PaiementRepository extends JpaRepository<Paiement, Long> {
   Paiement save(Paiement paiement);
   Page<Paiement> findByContratId(Long id, Pageable pageable);

   @Query("SELECT p FROM Paiement p " +
           "JOIN p.contrat c " + // 🌟 CORRECTION ICI : p.contrat au lieu de p.contratBail
           "JOIN c.preContrat pc " +
           "LEFT JOIN pc.demandeLocation dl " +
           "LEFT JOIN dl.bien b1 " +
           "LEFT JOIN pc.demandeVisite dv " +
           "LEFT JOIN dv.bienImmobilier b2 " +
           "WHERE (b1.gestionnaire.id = :gestionnaireId OR b2.gestionnaire.id = :gestionnaireId) " +
           "AND p.datePaiement BETWEEN :debut AND :fin")
    List<Paiement> findPaiementsByGestionnaireEtPeriode(
            @Param("gestionnaireId") Long gestionnaireId, 
            @Param("debut") LocalDate debut, 
            @Param("fin") LocalDate fin);
}
