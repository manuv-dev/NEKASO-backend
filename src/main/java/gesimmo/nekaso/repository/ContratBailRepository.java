package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.ContratBail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContratBailRepository extends JpaRepository<ContratBail, Long> {
    // List<ContratBail> findByLocataireId(Long locataireId);
    // List<ContratBail> findByBienId(Long bienId);
    // List <ContratBail> findByGestionnaireId(Long gestionnaireId);
    // @Query("SELECT COUNT(c) FROM ContratBail c WHERE c.demandeLocation.bienImmobilier.gestionnaire.id = :gestionnaireId AND c.dateDebut <= CURRENT_DATE")
    // int countByGestionnaireIdAndActif(Long gestionnaireId, boolean actif);
}
