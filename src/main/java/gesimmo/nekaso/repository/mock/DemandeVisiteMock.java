package gesimmo.nekaso.repository.mock;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.VisiteStatut;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.repository.LocataireRepository;

@Component
public class DemandeVisiteMock implements CommandLineRunner {

    private final DemandeVisiteRepository demandeVisiteRepository;
    private final BienImmobilierRepository bienImmobilierRepository;
    private final LocataireRepository locataireRepository;

    public DemandeVisiteMock(DemandeVisiteRepository demandeVisiteRepository,
            BienImmobilierRepository bienImmobilierRepository,
            LocataireRepository locataireRepository) {
        this.demandeVisiteRepository = demandeVisiteRepository;
        this.bienImmobilierRepository = bienImmobilierRepository;
        this.locataireRepository = locataireRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (demandeVisiteRepository.count() > 0) {
            return;
        }

        List<?> biens = bienImmobilierRepository.findAll();
        if (biens.isEmpty()) {
            // no biens yet, skip
            return;
        }

        Locataire loc1 = new Locataire();
        loc1.setNom("Sow");
        loc1.setPrenom("Fatou");
        loc1.setTelephone("771234567");
        loc1.setMotDePasse("password123");
        loc1.setRole("LOCATAIRE");
        loc1.setStatut("ACTIF");
        loc1.setDateCreation(LocalDateTime.now());
        locataireRepository.save(loc1);

        Locataire loc2 = new Locataire();
        loc2.setNom("Ndiaye");
        loc2.setPrenom("Ibrahima");
        loc2.setTelephone("785432167");
        loc2.setMotDePasse("password123");
        loc2.setRole("LOCATAIRE");
        loc2.setStatut("ACTIF");
        loc2.setDateCreation(LocalDateTime.now());
        locataireRepository.save(loc2);

        DemandeVisite d1 = new DemandeVisite();
        d1.setStatut(VisiteStatut.EN_ATTENTE);
        d1.setDateCreation(LocalDateTime.of(2024, 5, 15, 9, 0));
        d1.setLocataire(loc1);
        d1.setBienImmobilier((BienImmobilier) biens.get(1 % biens.size()));
        demandeVisiteRepository.save(d1);

        DemandeVisite d2 = new DemandeVisite();
        d2.setStatut(VisiteStatut.EN_ATTENTE);
        d2.setDateCreation(LocalDateTime.of(2024, 5, 10, 10, 0));
        d2.setLocataire(loc2);
        d2.setBienImmobilier((BienImmobilier) biens.get(0));
        demandeVisiteRepository.save(d2);
    }
}
