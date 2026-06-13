package gesimmo.nekaso.repository.mock;


import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.VisiteStatut;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.repository.LocataireRepository;

@Component
@Order(3)
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
      if(demandeVisiteRepository.count() == 0) {
        // Créer des demandes de visite pour les locataires et les biens immobiliers existants
        List<Locataire> locataires = locataireRepository.findAll();
        List<BienImmobilier> biens = bienImmobilierRepository.findAll();

        if (locataires.isEmpty() || biens.isEmpty()) {
          // Pas assez de données pour créer les demandes
          return;
        }

        for (int i = 0; i < 10; i++) {
          Locataire loc = locataires.get(i % locataires.size());
          BienImmobilier bien = biens.get(i % biens.size());

          DemandeVisite demande = DemandeVisite.builder()
              .locataire(loc)
              .bienImmobilier(bien)
              .dateCreation(LocalDateTime.now())
              .statut(VisiteStatut.EN_ATTENTE)
              .build();

          demandeVisiteRepository.save(demande);
        }

      }
      
    }
}
