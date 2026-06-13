package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.StatutDemande;
import gesimmo.nekaso.mapper.ContratMapper;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.ContratService;
import gesimmo.nekaso.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Gestionnaire;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements ContratService {

    private final ContratBailRepository contratRepo;
    private final DemandeLocationRepository demandeRepo;
    private final UserRepository userRepo;
    private final PdfService pdfService;
    private final ContratMapper ContratMapper;

   @Override
   public ContratDTO createContrat(long demandeLocationId, Double montantLoyer, Double montantCaution, String conditions, java.time.LocalDateTime dateDebut) {
        
        DemandeLocation demande = demandeRepo.findById(demandeLocationId)
            .orElseThrow(() -> new RuntimeException("Demande de location introuvable"));
        
        if (demande.getStatut() != StatutDemande.ACCEPTEE) {
            throw new RuntimeException("La demande de location doit être acceptée pour créer un contrat");
        }
        if (montantLoyer == null || montantCaution == null || conditions == null || dateDebut == null) {
            throw new IllegalArgumentException("Tous les champs sont obligatoires");
        }

        
        if(demandeRepo.findById(demandeLocationId).isEmpty()) {
            throw new RuntimeException("Demande de location introuvable");
        }
        ContratBail contrat = ContratBail.builder()
                .demandeLocation(demande)
                .montantLoyer(montantLoyer)
                .montantCaution(montantCaution)
                .conditions(conditions)
                .dateDebut(dateDebut)
                .dateSignature(java.time.LocalDateTime.now())
                .build();
        
        Locataire locataire = demande.getLocataire();
        Gestionnaire gestionnaire = demande.getBien().getGestionnaire();
        
        String cheminPDF = pdfService.genererContratPdf(contrat, locataire, gestionnaire);
        contrat.setCheminPDF(cheminPDF);

        ContratBail savedContrat = contratRepo.save(contrat);
        return ContratMapper.toDto(savedContrat);
    }

    @Override
    public ContratDTO getContratByBienIdAndLocataireId(Long bienId, Long locataireId) {
        ContratBail contrat = contratRepo.findByBienIdAndLocataireId(bienId, locataireId)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable pour le bien et le locataire spécifiés"));
        return ContratMapper.toDto(contrat);
    }

    @Override
    public byte[] telechargerContrat(Long contratId) {
        ContratBail contrat = contratRepo.findById(contratId)
                .orElseThrow(() -> new RuntimeException("Contrat introuvable"));
        try {
            return Files.readAllBytes(Paths.get(contrat.getCheminPDF()));
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la lecture du fichier PDF du contrat", e);
        }
    }

}
