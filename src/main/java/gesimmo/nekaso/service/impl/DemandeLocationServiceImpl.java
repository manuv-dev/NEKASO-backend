package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationCreateDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.StatutDemande;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.service.ContratService;
import gesimmo.nekaso.service.DemandeLocationService;
import gesimmo.nekaso.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DemandeLocationServiceImpl implements DemandeLocationService {

    private final DemandeLocationRepository demandeRepo;
    private final ContratService contratService;
    private final NotificationService notificationService;
    private final LocataireRepository locataireRepository;
    private final ContratBailRepository contratBailRepository;
    private final BienImmobilierRepository bienRepository;

    public DemandeLocationServiceImpl(DemandeLocationRepository demandeRepo
            , ContratService contratService
            , NotificationService notificationService
            , LocataireRepository locataireRepository
            , ContratBailRepository contratBailRepository
            , BienImmobilierRepository bienRepository
            ) {
        this.demandeRepo = demandeRepo;
        this.contratService = contratService;
        this.notificationService = notificationService;
        this.locataireRepository = locataireRepository;
        this.contratBailRepository = contratBailRepository;
        this.bienRepository = bienRepository;
    }

    @Override
    public DemandeLocationCreateDTO createDemandeLocation(Long id_Locataire, Long id_Bien) {
        boolean existeDeja = demandeRepo.existsByLocataireIdAndBienImmobilierIdAndStatut(
            id_Locataire,
            id_Bien
        );
        if (existeDeja) {
            throw new RuntimeException("Vous avez déjà une demande pour ce bien.");
        }else {
            Locataire locataire = locataireRepository.findById(id_Locataire)
                    .orElseThrow(() -> new ResourceNotFoundException("Le locataire avec l'ID " + id_Locataire + " n'a pas été trouvé"));
            BienImmobilier bien = bienRepository.findById(id_Bien)
                    .orElseThrow(() -> new ResourceNotFoundException("Le bien immobilier avec l'ID " + id_Bien + " n'a pas été trouvé"));

            DemandeLocation demandelocation = DemandeLocation.builder()
                    .locataire(locataire)
                    .bien(bien)
                    .statut("EN_ATTENTE")
                    .build();

            demandeRepo.save(demandelocation);
        }

        return null;
    }

    @Override
    public void refuserDemandeLocation(Long demandeId) {
        DemandeLocation demande = demandeRepo.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande de location non trouvée"));

        if (demande.getStatut() != null && demande.getStatut().equals("EN_ATTENTE")) {
            demande.setStatut("REFUSEE");
            demandeRepo.save(demande);
        } else {
            throw new RuntimeException("Cette demande ne peut pas être refusée");
        }
    }

    @Override
    public void  accepterDemandeLocation(Long demandeId) {
        DemandeLocation demande = demandeRepo.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande de location non trouvée"));

        if (demande.getStatut() != null && demande.getStatut().equals("EN_ATTENTE")) {
            demande.setStatut("ACCEPTEE");
            demandeRepo.save(demande);
        } else {
            throw new RuntimeException("Cette demande ne peut pas être acceptée");
        }
    }

    @Override
    public void annulerDemandeLocation(Long demandeId) {
        DemandeLocation demande = demandeRepo.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande de location non trouvée"));

        if (demande.getStatut() != null && demande.getStatut().equals("EN_ATTENTE")) {
            demande.setStatut("ANNULEE");
            demandeRepo.save(demande);
        } else {
            throw new RuntimeException("Cette demande ne peut pas être annulée");
        }
    }

    @Override
    public Page<DemandeLocation> getAllDemandesLocation(Pageable pageable, String statut, Long id_Locataire) {
        Page<DemandeLocation> demandesPage;
        if (statut == null || statut.isBlank()) {
            demandesPage = demandeRepo.findByLocataireId(id_Locataire, pageable);
        } else {
            demandesPage = demandeRepo.findByStatutAndLocataireId(
                    StatutDemande.valueOf(statut.toUpperCase()), id_Locataire, pageable
            );
        }
        return demandesPage;
    }

        @Override
        public  Page<DemandeLocation> getAllDemandesLocationByGestionnaireBienid(Pageable pageable, String statut, Long id_Gestionnaire) {
            Page<DemandeLocation> demandesPage;
            if (statut == null || statut.isBlank()) {
                demandesPage = demandeRepo.findByBienGestionnaireId(id_Gestionnaire, pageable);
            } else {
                demandesPage = demandeRepo.findByStatutAndBienGestionnaireId(
                        StatutDemande.valueOf(statut.toUpperCase()), id_Gestionnaire, pageable
                );
            }
            return demandesPage;
        }

}
