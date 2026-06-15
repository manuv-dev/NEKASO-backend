package gesimmo.nekaso.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.TypeBien;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BienImmobilierRepository extends JpaRepository<BienImmobilier, Long> {
        @Query("SELECT b FROM BienImmobilier b " +
                "WHERE (:libelle IS NULL OR b.libelle = :libelle) " +
                "AND (:adresse IS NULL OR b.adresse = :adresse) " +
                "AND (:surface IS NULL OR b.surface = :surface) " +
                "AND (:nombrePieces IS NULL OR b.nombrePieces = :nombrePieces) " +
                "AND (:loyer IS NULL OR b.loyer = :loyer)")
        Page<BienImmobilier> findByLibelleAndAdresseAndSurfaceAndNombrePiecesAndLoyer(@Param("libelle") String libelle, @Param("adresse") String adresse, @Param("surface") Double surface, @Param("nombrePieces") Integer nombrePieces, @Param("loyer") Double loyer, Pageable unpaged);
        @Query("SELECT b FROM BienImmobilier b WHERE b.gestionnaire.id = :gestionnaire_Id")
        Page<BienImmobilier> findByGestionnaireId(@Param("gestionnaire_Id") Long gestionnaireId, Pageable pageable);
        @Query("SELECT b FROM BienImmobilier b WHERE b.statutBien = 'DISPONIBLE'")
        Page<BienImmobilier> GetAllBienImmobilierDisponble(Pageable pageable);
        Page<BienImmobilier> findByStatutBien(StatutBien statutBien, Pageable pageable);

        Page<BienImmobilier> findByTypeBien(TypeBien typeBien,Pageable pageable);

        Page<BienImmobilier> findByStatutBienAndTypeBien(StatutBien statutBien, TypeBien typeBien,Pageable pageable);
        BienImmobilier save(BienImmobilier bienImmobilier);
}
