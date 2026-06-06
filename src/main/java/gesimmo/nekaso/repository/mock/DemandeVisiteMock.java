// package gesimmo.nekaso.repository.mock;


// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;


// import gesimmo.nekaso.repository.BienImmobilierRepository;
// import gesimmo.nekaso.repository.DemandeVisiteRepository;
// import gesimmo.nekaso.repository.LocataireRepository;

// @Component
// public class DemandeVisiteMock implements CommandLineRunner {

//     private final DemandeVisiteRepository demandeVisiteRepository;
//     private final BienImmobilierRepository bienImmobilierRepository;
//     private final LocataireRepository locataireRepository;

//     public DemandeVisiteMock(DemandeVisiteRepository demandeVisiteRepository,
//             BienImmobilierRepository bienImmobilierRepository,
//             LocataireRepository locataireRepository) {
//         this.demandeVisiteRepository = demandeVisiteRepository;
//         this.bienImmobilierRepository = bienImmobilierRepository;
//         this.locataireRepository = locataireRepository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//       if(demandeVisiteRepository.count() == 0) {
//         // Créer des demandes de visite pour les locataires et les biens immobiliers existants
      
//          demande1 = gesimmo.nekaso.entity.DemandeVisite.builder()
//                 .locataire(locataire1)
//                 .bienImmobilier(bien1)
//                 .dateCreation(java.time.LocalDate.now())
//                 .statut(gesimmo.nekaso.entity.enums.VisiteStatut.EN_ATTENTE)
//                 .build();

//         var demande2 = gesimmo.nekaso.entity.DemandeVisite.builder()
//                 .locataire(locataire2)
//                 .bienImmobilier(bien2)
//                 .dateCreation(java.time.LocalDate.now())
//                 .statut(gesimmo.nekaso.entity.enums.VisiteStatut.EN_ATTENTE)
//                 .build();

//         demandeVisiteRepository.save(demande1);
//         demandeVisiteRepository.save(demande2);
//       }
      
//     }
// }
