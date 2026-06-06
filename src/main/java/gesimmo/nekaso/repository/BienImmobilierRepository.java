package gesimmo.nekaso.repository;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
@Repository
public interface BienImmobilierRepository extends JpaRepository<BienImmobilier, Long> {
    Page<BienImmobilier> findByStatutBien(Statut statutBien,Pageable pageable);

    Page<BienImmobilier> findByTypeBien(TypeBien typeBien,Pageable pageable);

    Page<BienImmobilier> findByStatutBienAndTypeBien(Statut statutBien, TypeBien typeBien,Pageable pageable);
    

}
