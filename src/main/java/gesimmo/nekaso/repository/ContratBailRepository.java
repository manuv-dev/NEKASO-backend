package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.ContratBail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.Optional;

public interface ContratBailRepository extends JpaRepository<ContratBail, Long> {
    ContratBail save(ContratBail contratBail);
    Page<ContratBail> findAll(Pageable pageable);
    Optional<ContratBail> findById(Long id);
    @Query("SELECT c FROM ContratBail c WHERE c.demandeLocation.bien.gestionnaire.id = :gestionnaireId")
    Page<ContratBail> findByGestionnaireId(@Param("gestionnaireId") Long gestionnaireId, Pageable pageable);
    @Query("SELECT c FROM ContratBail c WHERE c.demandeLocation.locataire.id = :locataireId")
    Page<ContratBail> findByLocataireId(@Param("locataireId") Long locataireId, Pageable pageable);
}
