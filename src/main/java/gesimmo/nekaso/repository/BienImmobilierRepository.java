package gesimmo.nekaso.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;

public interface BienImmobilierRepository extends JpaRepository<BienImmobilier, Long> {
    List<BienImmobilier> findByStatutBien(Statut statutBien);

    List<BienImmobilier> findByTypeBien(TypeBien typeBien);

    List<BienImmobilier> findByStatutBienAndTypeBien(Statut statutBien, TypeBien typeBien);

}
