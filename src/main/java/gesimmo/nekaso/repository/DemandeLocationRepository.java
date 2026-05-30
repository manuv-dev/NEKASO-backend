package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.DemandeLocation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DemandeLocationRepository extends JpaRepository<DemandeLocation, Long> {

    List<DemandeLocation> findByBien_Gestionnaire_Id(Long gestionnaireId);

    List<DemandeLocation> findByLocataire_Id(Long locataireId);

    DemandeLocation findByIdAndBien_Gestionnaire_Id(Long demandeId, Long gestionnaireId);
}
