package gesimmo.nekaso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.TypeBien;

public interface BienImmobilierRepository extends JpaRepository<BienImmobilier, Long> {
    List<BienImmobilier> findByStatutBien(StatutBien statutBien);
    List<BienImmobilier> findByTypeBien(TypeBien typeBien);
    List<BienImmobilier> findByStatutBienAndTypeBien(StatutBien statutBien, TypeBien typeBien);
    List<BienImmobilier> findByGestionnaire_Id(Long gestionnaireId);
    List<BienImmobilier> findByNombrePiecesBetween(Integer nombrePiecesMin, Integer nombrePiecesMax);
    List<BienImmobilier> findByLoyerBetween(Double loyerMin, Double loyerMax);
    List<BienImmobilier> findByTypeBienAndNombrePiecesAndLoyerBetween(TypeBien typeBien, Integer nombrePieces, Double loyerMin, Double loyerMax);
}
