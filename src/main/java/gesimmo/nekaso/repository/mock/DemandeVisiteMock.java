package gesimmo.nekaso.repository.mock;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;


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
      
      
    }
}
