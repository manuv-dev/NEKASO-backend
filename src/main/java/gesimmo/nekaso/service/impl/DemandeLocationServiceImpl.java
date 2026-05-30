package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.service.ContratService;
import gesimmo.nekaso.service.DemandeLocationService;
import gesimmo.nekaso.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DemandeLocationServiceImpl implements DemandeLocationService {

    private final DemandeLocationRepository demandeRepo;
    private final ContratService contratService;
    private final NotificationService notificationService;

    @Override
    public DemandeLocation creerDemande(DemandeLocation demande) {
        demande.setStatut("EN_ATTENTE");
        return demandeRepo.save(demande);
    }

    // Implémentation
    @Override
    @Transactional
    public ContratBail validerDemande(Long demandeId, ContratDTO dto) {
        DemandeLocation demande = demandeRepo.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        demande.setStatut("VALIDEE");
        demandeRepo.save(demande);

        dto.setDemandeLocationId(demande.getId());
        ContratBail contrat = contratService.creerContrat(dto);

        notificationService.envoyerNotification(
                demande.getLocataire().getUser(),
                "Votre demande de location a été validée, le contrat est disponible.",
                "LOCATION"
        );

        return contrat;
    }


    @Override
    public DemandeLocation rejeterDemande(Long demandeId) {
        DemandeLocation demande = demandeRepo.findById(demandeId)
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));
        demande.setStatut("REJETEE");
        notificationService.envoyerNotification(
                demande.getLocataire().getUser(),
                "Votre demande de location a été rejetée.",
                "LOCATION"
        );

        return demandeRepo.save(demande);
    }

    @Override
    public List<DemandeLocation> getDemandesParGestionnaire(Long gestionnaireId) {
        return demandeRepo.findByBien_Gestionnaire_Id(gestionnaireId);
    }

    @Override
    public List<DemandeLocation> getDemandesParLocataire(Long locataireId) {
        return demandeRepo.findByLocataire_Id(locataireId);
    }
}
