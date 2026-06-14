package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.ContratBail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ContratBailRepository extends JpaRepository<ContratBail, Long> {

    ContratBail save(ContratBail contrat);

    @Query("SELECT c FROM ContratBail c WHERE c.demandeLocation.bien.id = :bienId AND c.demandeLocation.locataire.id = :locataireId")
    Optional<ContratBail> findByBienIdAndLocataireId(
        @Param("bienId") Long bienId, 
        @Param("locataireId") Long locataireId);
}
