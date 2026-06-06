package gesimmo.nekaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gesimmo.nekaso.entity.DemandeVisite;
@Repository
public interface DemandeVisiteRepository extends JpaRepository<DemandeVisite, Long> {
    


}
