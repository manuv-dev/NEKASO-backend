package gesimmo.nekaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.entity.enums.VisiteStatut;
@Repository
public interface DemandeVisiteRepository extends JpaRepository<DemandeVisite, Long> {
    
boolean existsByLocataireIdAndBienImmobilierIdAndStatut(Long locataireId, Long bienId, VisiteStatut statut);


}
