package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.ContratBail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContratBailRepository extends JpaRepository<ContratBail, Long> {
    List<ContratBail> findByDemandeLocation_Locataire_Id(Long locataireId);

    List<ContratBail> findByDemandeLocation_Bien_Id(Long bienId);

    List<ContratBail> findByDemandeLocation_Bien_Gestionnaire_Id(Long gestionnaireId);
}
