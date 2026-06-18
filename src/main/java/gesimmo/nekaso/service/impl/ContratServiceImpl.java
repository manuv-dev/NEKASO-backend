package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.StatutContrat;
import gesimmo.nekaso.entity.enums.StatutDemande;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.ContratService;
import gesimmo.nekaso.service.PdfService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import gesimmo.nekaso.mapper.ContratMapper;
import gesimmo.nekaso.service.CloudinaryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import gesimmo.nekaso.service.DemandeLocationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements ContratService {

    private final ContratBailRepository contratRepo;
    private final DemandeLocationRepository demandeRepo;
    private final UserRepository usersRepo;
    private final PdfService pdfService;
    private final ContratMapper contratMapper;
    private final CloudinaryService cloudinaryService;

    @Override
    @Transactional
    public ContratDTO creerContrat(ContratDTO dto) {

        DemandeLocation demande = demandeRepo.findById(dto.getDemandeLocationId())
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        if (demande.getStatut() == null || demande.getStatut() != StatutDemande.ACCEPTEE) {
            throw new RuntimeException("La demande de location n'est pas acceptée. Impossible de créer le contrat.");
        }

        if (demande.getLocataire() == null || demande.getLocataire().getUser() == null) {
            throw new RuntimeException("Données du locataire incomplètes pour générer le contrat.");
        }
        if (demande.getBien() == null || demande.getBien().getGestionnaire() == null || demande.getBien().getGestionnaire().getUser() == null) {
            throw new RuntimeException("Le bien immobilier n'a pas de gestionnaire assigné.");
        }

        Double loyer = demande.getBien().getLoyer();
        Double Caution = loyer * 2; 
        ContratBail contrat = ContratBail.builder()
                .dateSignature(dto.getDateSignature())
                .dateDebut(dto.getDateDebut())
                .montantLoyer(loyer)
                .montantCaution(Caution)
                .conditions(dto.getConditions())
                .statutContrat(StatutContrat.ACTIF)
                .demandeLocation(demande)
                .build();


        contrat = contratRepo.save(contrat);

        demande.getBien().setStatutBien(StatutBien.LOUE);

        String typeBien;
        switch (demande.getBien().getTypeBien()) {
            case APPARTEMENT -> typeBien = "Appartement";
            case CHAMBRE -> typeBien = "Chambre";
            case LOCAL -> typeBien = "Local commercial";
            case STUDIO -> typeBien = "Studio";
            default -> typeBien = "Type inconnu";
        }

        String libelle = demande.getBien().getLibelle() ;
        // 5. Récupération des utilisateurs pour le PDF
        User locataireUser = demande.getLocataire().getUser();
        User gestionnaireUser = demande.getBien().getGestionnaire().getUser();

        // 6. Génération du PDF en mémoire vive (byte[]) au lieu d'un fichier local
        byte[] pdfBytes = pdfService.genererContratPdf(contrat, locataireUser, gestionnaireUser, typeBien, libelle);

        // 7. Envoi du fichier sur Cloudinary et récupération du lien URL public HTTPS
        String nomFichierUnique = "contrat_bail_" + contrat.getId();
        String urlCloudinaryPdf = cloudinaryService.uploadPdf(pdfBytes, nomFichierUnique);

        // 8. Mise à jour de l'entité avec l'URL Cloudinary et sauvegarde finale
        contrat.setCheminPDF(urlCloudinaryPdf);
        contrat = contratRepo.save(contrat);

        // 9. Retour du DTO contenant l'URL cloud
        return contratMapper.toDTO(contrat);
    }
    @Override
    public ContratBail getContratById(Long id) {
        return contratRepo.findById(id).orElseThrow(() -> new RuntimeException("Contrat introuvable"));
    }

    @Override
    public Page<ContratDTO> getContratsParLocataire(Long locataireId, Pageable pageable) {

        Page<ContratBail> contrats = contratRepo.findByLocataireId(locataireId, pageable);
        
        if (contrats.isEmpty()) {
            throw new RuntimeException("Aucun contrat trouvé pour ce locataire");
        }
        
        return contrats.map(contratMapper::toDTO);
    }

    @Override
    public Page<ContratDTO> getContratsParGestionnaire(Long gestionnaireId, Pageable pageable) {
        // 1. Récupération des contrats du gestionnaire directement depuis la BDD
        Page<ContratBail> contrats = contratRepo.findByGestionnaireId(gestionnaireId, pageable);
        
        if (contrats.isEmpty()) {
            throw new RuntimeException("Aucun contrat trouvé pour ce gestionnaire");
        }
        
        return contrats.map(contratMapper::toDTO);
    }
}
