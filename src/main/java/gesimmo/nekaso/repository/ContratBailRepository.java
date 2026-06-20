package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.ContratBail;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContratBailRepository extends JpaRepository<ContratBail, Long> {
    Page<ContratBail> findByLocataireId(Long locataireId, Pageable pageable);

    @Query("SELECT c FROM ContratBail c " +
           "JOIN c.preContrat p " +
           "LEFT JOIN p.demandeLocation dl " +
           "LEFT JOIN dl.bien b1 " +
           "LEFT JOIN p.demandeVisite dv " +
           "LEFT JOIN dv.bienImmobilier b2 " +
           "WHERE (dl IS NOT NULL AND b1.gestionnaire.id = :gestionnaireId) " +
           "OR (dv IS NOT NULL AND b2.gestionnaire.id = :gestionnaireId)")
    Page<ContratBail> findByGestionnaireId(@Param("gestionnaireId") Long gestionnaireId, Pageable pageable);

    
    @Query("SELECT c FROM ContratBail c " +
       "JOIN c.preContrat p " +
       "LEFT JOIN p.demandeLocation dl " +
       "LEFT JOIN dl.bien b1 " +
       "LEFT JOIN p.demandeVisite dv " +
       "LEFT JOIN dv.bienImmobilier b2 " +
       "WHERE (b1.gestionnaire.id = :gestionnaireId OR b2.gestionnaire.id = :gestionnaireId) " +
       "AND c.statutContrat = 'ACTIF'")
    List<ContratBail> findContratsActifsParGestionnaire(@Param("gestionnaireId") Long gestionnaireId);

}
