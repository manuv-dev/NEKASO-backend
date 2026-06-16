//package gesimmo.nekaso.repository;
//
//import gesimmo.nekaso.entity.DemandeLocation;
//import gesimmo.nekaso.entity.enums.StatutDemande;
//import org.springframework.data.domain.Page;
//import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.repository.query.Param;
//
//import java.util.Optional;
//
//public interface DemandeLocationRepository extends JpaRepository<DemandeLocation, Long> {
//    @Query("SELECT COUNT(d) > 0 FROM DemandeLocation d WHERE d.locataire.id = :locataireId AND d.bien.id = :bienId")
//    boolean existsByLocataireIdAndBienImmobilierIdAndStatut(@Param("locataireId") Long locataireId, @Param("bienId") Long bienId);
//    Page<DemandeLocation> findByLocataireId(Long locataireId, Pageable pageable);
//    Page<DemandeLocation> findByStatutAndLocataireId(StatutDemande locataireId, Long statut, Pageable pageable);
//    Page<DemandeLocation> findByBienImmobilierId(Long bien, Pageable pageable);
//    @Query("SELECT d FROM DemandeLocation d WHERE d.bien.gestionnaire.id = :gestionnaireId")
//    Page<DemandeLocation> findByBienId(Long bienId, Pageable pageable);
//    DemandeLocation save(DemandeLocation demandeLocation);
//    Optional <DemandeLocation> findById(Long id);
//    Page<DemandeLocation> findByBienGestionnaireId(Long idGestionnaire, Pageable pageable);
//    Page<DemandeLocation> findByStatutAndBienGestionnaireId(StatutDemande statutDemande, Long idGestionnaire, Pageable pageable);
//}
package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.enums.StatutDemande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface DemandeLocationRepository extends JpaRepository<DemandeLocation, Long> {

    @Query("SELECT COUNT(d) > 0 FROM DemandeLocation d WHERE d.locataire.id = :locataireId AND d.bien.id = :bienId")
    boolean existsByLocataireIdAndBienImmobilierIdAndStatut(@Param("locataireId") Long locataireId, @Param("bienId") Long bienId);

    Page<DemandeLocation> findByLocataireId(Long locataireId, Pageable pageable);

    Page<DemandeLocation> findByStatutAndLocataireId(StatutDemande statut, Long locataireId, Pageable pageable);

    // CORRECTION : "bienImmobilierId" devient "bienId" pour cibler d.bien.id
    Page<DemandeLocation> findByBienId(Long bienId, Pageable pageable);

    @Query("SELECT d FROM DemandeLocation d WHERE d.bien.gestionnaire.id = :gestionnaireId")
    Page<DemandeLocation> findByBienImmobilierGestionnaireId(@Param("gestionnaireId") Long gestionnaireId, Pageable pageable);

    DemandeLocation save(DemandeLocation demandeLocation);

    Optional<DemandeLocation> findById(Long id);

    Page<DemandeLocation> findByBienGestionnaireId(Long idGestionnaire, Pageable pageable);
    Page<DemandeLocation> findAllByStatut(StatutDemande statutDemande, Pageable pageable);
}
