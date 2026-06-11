package gesimmo.nekaso.repository;

import java.util.List;

import gesimmo.nekaso.entity.enums.Statut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.TypeBien;
import org.springframework.data.jpa.repository.Query;

public interface BienImmobilierRepository extends JpaRepository<BienImmobilier, Long> {
    int countByGestionnaireId(Long gestionnaireId);
  
    List<BienImmobilier> findByStatutBienAndTypeBien(StatutBien statutBien, TypeBien typeBien);
    List<BienImmobilier> findByGestionnaire_Id(Long gestionnaireId);
    List<BienImmobilier> findByNombrePiecesBetween(Integer nombrePiecesMin, Integer nombrePiecesMax);
    List<BienImmobilier> findByLoyerBetween(Double loyerMin, Double loyerMax);
    List<BienImmobilier> findByTypeBienAndNombrePiecesAndLoyerBetween(TypeBien typeBien, Integer nombrePieces, Double loyerMin, Double loyerMax);
    @Query("SELECT b.typeBien, COUNT(b) FROM BienImmobilier b WHERE b.gestionnaire.id = :gestionnaireId GROUP BY b.typeBien")
    List<Object[]> countByTypeBienAndGestionnaire(Long gestionnaireId);
    Page<BienImmobilier> findByStatutBien(Statut statut, Pageable pageable);

    Page<BienImmobilier> findByTypeBien(TypeBien typeBien,Pageable pageable);

    Page<BienImmobilier> findByStatutBienAndTypeBien(Statut statutBien, TypeBien typeBien,Pageable pageable);
}
