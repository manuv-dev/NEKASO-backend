package gesimmo.nekaso.config;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.Role;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.StatutDemande;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.repository.GestionnaireRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final ContratBailRepository contratBailRepository;
    private final DemandeLocationRepository demandeLocationRepository;
    private final LocataireRepository locataireRepository;
    private final BienImmobilierRepository bienImmobilierRepository;
    private final GestionnaireRepository gestionnaireRepository;
    private final UserRepository userRepository;

    @Override
    public void run(String... args) {
        List<ContratBail> contrats = contratBailRepository.findAll();
        ContratBail contrat = contrats.isEmpty() ? new ContratBail() : contrats.get(0);

        if (contrat.getDemandeLocation() == null) {
            User locataireUser = userRepository.save(User.builder()
                    .nom("Diallo")
                    .prenom("Awa")
                    .telephone("775000000")
                    .motDePasse("password")
                    .role(Role.LOCATAIRE)
                    .statut("ACTIF")
                    .build());

            User gestionnaireUser = userRepository.save(User.builder()
                    .nom("Kane")
                    .prenom("Moussa")
                    .telephone("771111111")
                    .motDePasse("password")
                    .role(Role.GESTIONNAIRE)
                    .statut("ACTIF")
                    .build());

            Locataire locataire = locataireRepository.save(Locataire.builder()
                    .user(locataireUser)
                    .build());

            Gestionnaire gestionnaire = new Gestionnaire();
            gestionnaire.setUser(gestionnaireUser);
            gestionnaire = gestionnaireRepository.save(gestionnaire);

            BienImmobilier bien = new BienImmobilier();
            bien.setTypeBien(TypeBien.APPARTEMENT);
            bien.setLibelle("Appartement test");
            bien.setAdresse("Dakar");
            bien.setSurface(80.0);
            bien.setNombrePieces(3);
            bien.setLoyer(250000.0);
            bien.setStatutBien(StatutBien.LOUE);
            bien.setDescription("Bien de test");
            bien.setDateAjout(LocalDate.now());
            bien.setGestionnaire(gestionnaire);
            bien = bienImmobilierRepository.save(bien);

            DemandeLocation demande = demandeLocationRepository.save(DemandeLocation.builder()
                    .statut(StatutDemande.ACCEPTEE)
                    .dateDemande(LocalDateTime.now())
                    .locataire(locataire)
                    .bien(bien)
                    .build());

            contrat.setDateSignature(LocalDate.now());
            contrat.setMontantLoyer(250000.0);
            contrat.setMontantCaution(500000.0);
            contrat.setConditions("Contrat de test pour validation locale");
            contrat.setDateDebut(LocalDate.now());
            contrat.setDemandeLocation(demande);

            contratBailRepository.save(contrat);
        }
    }
}
