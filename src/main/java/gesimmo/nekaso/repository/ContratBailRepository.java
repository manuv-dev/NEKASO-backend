package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.ContratBail;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ContratBailRepository extends JpaRepository<ContratBail, Long> {
    List<ContratBail> findByLocataireId(Long locataireId);
    List<ContratBail> findByBienId(Long bienId);
    List <ContratBail> findByGestionnaireId(Long gestionnaireId);
}
