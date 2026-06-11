package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.ContratService;
import gesimmo.nekaso.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements ContratService {

    private final ContratBailRepository contratRepo;
    private final DemandeLocationRepository demandeRepo;
    private final UserRepository usersRepo;
    private final PdfService pdfService;

    // @Override
    // public ContratBail creerContrat(ContratDTO dto) {
    //     DemandeLocation demande = demandeRepo.findById(dto.getDemandeLocationId())
    //             .orElseThrow(() -> new RuntimeException("Demande introuvable"));

    //     // Création du contrat
    //     ContratBail contrat = ContratBail.builder()
    //             .dateSignature(dto.getDateSignature())
    //             .dateDebut(dto.getDateDebut())
    //             .montantLoyer(dto.getMontantLoyer())
    //             .montantCaution(dto.getMontantCaution())
    //             .conditions(dto.getConditions())
    //             .demandeLocation(demande)
    //             .build();

    //     contrat = contratRepo.save(contrat);

    //     // Récupérer infos locataire et gestionnaire
    //     User locataireUser = demande.getLocataire().getUser();
    //     User gestionnaireUser = demande.getBien().getGestionnaire().getUser();

    //     // Générer PDF et mettre chemin
    //     String cheminPDF = pdfService.genererContratPdf(contrat, locataireUser, gestionnaireUser);
    //     contrat.setCheminPDF(cheminPDF);

    //     return contratRepo.save(contrat);
    // }

    // @Override
    // public List<ContratBail> getContratsParLocataire(Long locataireId) {
    //     return contratRepo.findByLocataireId(locataireId);
    // }

    // @Override
    // public List<ContratBail> getContratsParBien(Long bienId) {
    //     return contratRepo.findByBienId(bienId);
    // }

    // @Override
    // public List<ContratBail> getContratsParGestionnaire(Long gestionnaireId) {
    //     return contratRepo.findByGestionnaireId(gestionnaireId);
    // }

    @Override
    public ContratBail getContratById(Long id) {
        return contratRepo.findById(id).orElseThrow(() -> new RuntimeException("Contrat introuvable"));
    }
}
