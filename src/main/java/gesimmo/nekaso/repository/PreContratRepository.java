package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.PreContrat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PreContratRepository extends JpaRepository<PreContrat, Long> {
    @Query("SELECT p FROM PreContrat p WHERE p.locataire.id = :locataireId")
    Page<PreContrat> findByLocataireUserId(@Param("locataireId") Long locataireId, Pageable pageable);
    Page<PreContrat> findByLocataireId(Long locataireId, Pageable pageable);
    Page<PreContrat> findAll(Pageable pageable);
    @Query("SELECT p FROM PreContrat p " +
           "LEFT JOIN p.demandeLocation dl " +
           "LEFT JOIN dl.bien b1 " +
           "LEFT JOIN p.demandeVisite dv " +
           "LEFT JOIN dv.bienImmobilier b2 " +
           "WHERE (dl IS NOT NULL AND b1.gestionnaire.id = :gestionnaireId) " +
           "OR (dv IS NOT NULL AND b2.gestionnaire.id = :gestionnaireId)")
    Page<PreContrat> findByGestionnaireId(@Param("gestionnaireId") Long gestionnaireId, Pageable pageable);
}