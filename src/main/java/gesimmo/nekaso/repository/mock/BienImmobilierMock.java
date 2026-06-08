package gesimmo.nekaso.repository.mock;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.PhotoBien;
import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.PhotoBienRepository;

@Component
public class BienImmobilierMock implements CommandLineRunner {
    private final BienImmobilierRepository bienImmobilierRepository;
    private final PhotoBienRepository photoBienRepository;

    public BienImmobilierMock(BienImmobilierRepository bienImmobilierRepository, PhotoBienRepository photoBienRepository) {
        this.bienImmobilierRepository = bienImmobilierRepository;
        this.photoBienRepository = photoBienRepository;
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

        BienImmobilier bien6 = new BienImmobilier();
        bien6.setTypeBien(TypeBien.STUDIO);
        bien6.setAdresse("987 Ambroise  de Strasbourg, 67000 Strasbourg");
        bien6.setSurface(200.0);
        bien6.setNombrePieces(6);
        bien6.setLoyer(3000.0);
        bien6.setStatutBien(Statut.DISPONIBLE);
        bien6.setDescription("Superbe appartement avec vue sur la cathédrale.");
        bien6.setDateAjout(LocalDate.now());
        bienImmobilierRepository.save(bien6);

        // Créer les photos (10 exemples)
        PhotoBien photo1 = new PhotoBien();
        photo1.setUrlPhoto("https://exemple.com/photos/bien1/facade.jpg");
        photo1.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo1.setBienImmobilier(bien1);
        photoBienRepository.save(photo1);

        PhotoBien photo2 = new PhotoBien();
        photo2.setUrlPhoto("https://exemple.com/photos/bien1/salon.jpg");
        photo2.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo2.setBienImmobilier(bien1);
        photoBienRepository.save(photo2);

        PhotoBien photo3 = new PhotoBien();
        photo3.setUrlPhoto("https://exemple.com/photos/bien2/entree.jpg");
        photo3.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo3.setBienImmobilier(bien2);
        photoBienRepository.save(photo3);

        PhotoBien photo4 = new PhotoBien();
        photo4.setUrlPhoto("https://exemple.com/photos/bien2/cuisine.jpg");
        photo4.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo4.setBienImmobilier(bien2);
        photoBienRepository.save(photo4);

        PhotoBien photo5 = new PhotoBien();
        photo5.setUrlPhoto("https://exemple.com/photos/bien3/chambre.jpg");
        photo5.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo5.setBienImmobilier(bien3);
        photoBienRepository.save(photo5);

        PhotoBien photo6 = new PhotoBien();
        photo6.setUrlPhoto("https://exemple.com/photos/bien3/salle-eau.jpg");
        photo6.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo6.setBienImmobilier(bien3);
        photoBienRepository.save(photo6);

        PhotoBien photo7 = new PhotoBien();
        photo7.setUrlPhoto("https://exemple.com/photos/bien4/bureau.jpg");
        photo7.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo7.setBienImmobilier(bien4);
        photoBienRepository.save(photo7);

        PhotoBien photo8 = new PhotoBien();
        photo8.setUrlPhoto("https://exemple.com/photos/bien4/balcon.jpg");
        photo8.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo8.setBienImmobilier(bien4);
        photoBienRepository.save(photo8);

        PhotoBien photo9 = new PhotoBien();
        photo9.setUrlPhoto("https://exemple.com/photos/bien5/jardin.jpg");
        photo9.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo9.setBienImmobilier(bien5);
        photoBienRepository.save(photo9);

        PhotoBien photo10 = new PhotoBien();
        photo10.setUrlPhoto("https://exemple.com/photos/bien6/vue-panoramique.jpg");
        photo10.setDateUpload(LocalDateTime.from(LocalDate.now()));
        photo10.setBienImmobilier(bien6);
        photoBienRepository.save(photo10);
       
    }}
    
}
