package gesimmo.nekaso.repository.mock;

import java.time.LocalDate;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.repository.BienImmobilierRepository;

@Component
public class BienImmobilierMock implements CommandLineRunner {
    private final BienImmobilierRepository bienImmobilierRepository;

    public BienImmobilierMock(BienImmobilierRepository bienImmobilierRepository) {
        this.bienImmobilierRepository = bienImmobilierRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        
       if(bienImmobilierRepository.count() == 0) {
        BienImmobilier bien1 = new BienImmobilier();
        bien1.setTypeBien(TypeBien.APPARTEMENT);
        bien1.setAdresse("123 Rue de Paris, 75001 Paris");
        bien1.setSurface(75.0);
        bien1.setNombrePieces(3);
        bien1.setLoyer(1200.0);
        bien1.setStatutBien(Statut.DISPONIBLE);
        bien1.setDescription("Bel appartement au cœur de Paris.");
        bien1.setDateAjout(LocalDate.now());

        bienImmobilierRepository.save(bien1);

        BienImmobilier bien2 = new BienImmobilier();
        bien2.setTypeBien(TypeBien.APPARTEMENT);
        bien2.setAdresse("456 Avenue de Lyon, 69001 Lyon");
        bien2.setSurface(150.0);
        bien2.setNombrePieces(5);
        bien2.setLoyer(2500.0);
        bien2.setStatutBien(Statut.DISPONIBLE);
        bien2.setDescription("Magnifique maison en plein centre-ville.");
        bien2.setDateAjout(LocalDate.now());

        bienImmobilierRepository.save(bien2);

        BienImmobilier bien3 = new BienImmobilier();
        bien3.setTypeBien(TypeBien.CHAMBRE);
        bien3.setAdresse("789 Boulevard de Marseille, 13001 Marseille");
        bien3.setSurface(30.0);
        bien3.setNombrePieces(1);
        bien3.setLoyer(800.0);
        bien3.setStatutBien(Statut.RESERVE);
        bien3.setDescription("Charmant studio en plein cœur de Marseille.");
        bien3.setDateAjout(LocalDate.now());

        bienImmobilierRepository.save(bien3);

        BienImmobilier bien4 = new BienImmobilier();
        bien4.setTypeBien(TypeBien.STUDIO);
        bien4.setAdresse("321 Rue de Bordeaux, 33000 Bordeaux");
        bien4.setSurface(60.0);
        bien4.setNombrePieces(2);
        bien4.setLoyer(1000.0); 
        bien4.setStatutBien(Statut.LOUE);
        bien4.setDescription("Appartement cosy à proximité du centre-ville.");
        bien4.setDateAjout(LocalDate.now());
        bienImmobilierRepository.save(bien4);

        BienImmobilier bien5 = new BienImmobilier();
        bien5.setTypeBien(TypeBien.APPARTEMENT);
        bien5.setAdresse("654 Avenue de Lille, 59000 Lille");
        bien5.setSurface(120.0);
        bien5.setNombrePieces(4);
        bien5.setLoyer(2000.0);
        bien5.setStatutBien(Statut.ARCHIVE);
        bien5.setDescription("Belle maison avec jardin à Lille.");
        bien5.setDateAjout(LocalDate.now());
        bienImmobilierRepository.save(bien5);
        

        
       
    }}
    
}
