package gesimmo.nekaso.repository.mock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.Role;
import gesimmo.nekaso.repository.LocataireRepository;

@Component
public class LocataireMock implements CommandLineRunner {
    private LocataireRepository locataireRepository;

    public LocataireMock(LocataireRepository locataireRepository) {
        this.locataireRepository = locataireRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (locataireRepository.count() == 0) {
            Locataire locataire1 = new Locataire();
            locataire1.setNom("Doe");
            locataire1.setPrenom("John");
            locataire1.setRole(Role.LOCATAIRE);
            locataire1.setMotDePasse("password");
            locataire1.setTelephone("123456789");
            locataire1.setStatut("Active");
            locataireRepository.save(locataire1);

            Locataire locataire2 = new Locataire();
            locataire2.setNom("Smith");
            locataire2.setPrenom("Jane");
            locataire2.setRole(Role.LOCATAIRE);
            locataire2.setMotDePasse("password");
            locataire2.setTelephone("123456789");
            locataire2.setStatut("Active");
            locataireRepository.save(locataire2);

            Locataire locataire3 = new Locataire();
            locataire3.setNom("Johnson");
            locataire3.setPrenom("Bob");
            locataire3.setRole(Role.LOCATAIRE);
            locataire3.setMotDePasse("password");
            locataire3.setTelephone("123456789");
            locataire3.setStatut("Inactive");
            locataireRepository.save(locataire3);
        }
    }
}
