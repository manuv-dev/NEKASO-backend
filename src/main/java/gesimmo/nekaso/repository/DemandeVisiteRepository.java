package gesimmo.nekaso.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import gesimmo.nekaso.entity.DemandeVisite;

@Repository
public interface DemandeVisiteRepository extends JpaRepository<DemandeVisite, Long> {
  
@Query("SELECT d FROM DemandeVisite d JOIN d.locataire l " +
           "WHERE (:nom = '' OR LOWER(l.nom) LIKE LOWER(CONCAT('%', :nom, '%'))) " +
           "AND (:prenom = '' OR LOWER(l.prenom) LIKE LOWER(CONCAT('%', :prenom, '%')))")
Page<DemandeVisite> rechercherParNomEtPrenomLocataire(@Param("nom") String nom, @Param("prenom") String prenom,  Pageable pageable);    DemandeVisite findDemandeVisiteById(Long id);
    
  Page<DemandeVisite> findAll(Pageable page );
}
