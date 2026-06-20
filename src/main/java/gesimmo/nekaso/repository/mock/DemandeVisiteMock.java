// package gesimmo.nekaso.repository.mock;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.stereotype.Component;

// import java.time.LocalDateTime;
// import java.util.List;
// import java.util.Optional;

// import gesimmo.nekaso.entity.BienImmobilier;
// import gesimmo.nekaso.entity.DemandeVisite;
// import gesimmo.nekaso.entity.Locataire;
// import gesimmo.nekaso.entity.enums.VisiteStatut;
// import gesimmo.nekaso.repository.BienImmobilierRepository;
// import gesimmo.nekaso.repository.DemandeVisiteRepository;
// import gesimmo.nekaso.repository.LocataireRepository;

// @Component
// @Order(3)
// public class DemandeVisiteMock implements CommandLineRunner {

//     private final DemandeVisiteRepository demandeVisiteRepository;
//     private final BienImmobilierRepository bienImmobilierRepository;
//     private final LocataireRepository locataireRepository;

//     public DemandeVisiteMock(DemandeVisiteRepository demandeVisiteRepository,
//                              BienImmobilierRepository bienImmobilierRepository,
//                              LocataireRepository locataireRepository) {
//         this.demandeVisiteRepository = demandeVisiteRepository;
//         this.bienImmobilierRepository = bienImmobilierRepository;
//         this.locataireRepository = locataireRepository;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (demandeVisiteRepository.count() == 0) {
            
//             List<Locataire> locataires = locataireRepository.findAll();
            
//             // 1. On récupère proprement le bien cible de manière sécurisée
//             Long bienId = 1L; 
//             Optional<BienImmobilier> bienOptional = bienImmobilierRepository.findById(bienId);

//             // 2. Condition de sécurité : on valide la présence des locataires ET du bien cible
//             if (locataires.isEmpty() || bienOptional.isEmpty()) {
//                 System.out.println("⚠️ Abandon du mock : Aucun locataire trouvé ou le Bien ID " + bienId + " n'existe pas.");
//                 return;
//             }

//             // On extrait le bien puisqu'on est sûr qu'il existe à ce stade
//             BienImmobilier bienCible = bienOptional.get();

//             // 3. Boucle de création de 10 demandes sur ce même bien
//             for (int i = 0; i < 10; i++) {
//                 Locataire loc = locataires.get(i % locataires.size());

//                 DemandeVisite demande = DemandeVisite.builder()
//                         .locataire(loc)
//                         .bienImmobilier(bienCible) // Le même bien pour toutes les itérations
//                         .dateCreation(LocalDateTime.now())
//                         .statut(VisiteStatut.EN_ATTENTE)
//                         .build();

//                 demandeVisiteRepository.save(demande);
//             }
            
//             System.out.println("✅ Test configuré : 10 demandes de visite créées pour le bien ID " + bienId);
//         }
//     }
// }