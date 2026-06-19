package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.ContratDTO.ContratBailRequestDTO;
import gesimmo.nekaso.dto.ContratDTO.ContratBailResponseDTO;
import gesimmo.nekaso.entity.*;
import gesimmo.nekaso.entity.enums.StatutContrat;
import gesimmo.nekaso.entity.enums.StatutPreContrat;
import gesimmo.nekaso.exception.EntityNotFoundException;
import gesimmo.nekaso.mapper.ContratBailMapper;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.PreContratRepository;
import gesimmo.nekaso.service.CloudinaryService;
import gesimmo.nekaso.service.ContratBailService;
import gesimmo.nekaso.service.PdfService;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ContratBailServiceImpl implements ContratBailService {

    private final ContratBailRepository contratRepo;
    private final PreContratRepository preContratRepo;
    private final PdfService pdfService;
    private final CloudinaryService cloudinaryService;
    private final ContratBailMapper contratMapper;

    @Override
    @Transactional
    public ContratBailResponseDTO creerContrat(ContratBailRequestDTO dto) {
        PreContrat preContrat = preContratRepo.findById(dto.getPreContratId())
                .orElseThrow(() -> new EntityNotFoundException("Impossible de générer le contrat : Pré-contrat introuvable avec l'ID : " + dto.getPreContratId()));

        if (preContrat.getStatutPreContrat() != StatutPreContrat.VALIDER) {
            throw new IllegalArgumentException("Action impossible : Le pré-contrat doit posséder le statut 'VALIDER' pour éditer un contrat de bail définitif. Statut actuel : " + preContrat.getStatutPreContrat());
        }

        Locataire locataireUser = preContrat.getLocataire();
        if (locataireUser == null) {
            throw new EntityNotFoundException("Erreur d'intégrité : Aucun locataire n'est rattaché à ce pré-contrat.");
        }

        BienImmobilier bien = preContrat.getDemandeLocation() != null 
                ? preContrat.getDemandeLocation().getBien() 
                : preContrat.getDemandeVisite().getBienImmobilier();

        if (bien == null) {
            throw new EntityNotFoundException("Erreur d'intégrité : Aucun bien immobilier n'est rattaché aux demandes de ce pré-contrat.");
        }

        Gestionnaire gestionnaireUser = bien.getGestionnaire();
        if (gestionnaireUser == null) {
            throw new EntityNotFoundException("Erreur critique : Le bien '" + bien.getLibelle() + "' n'est lié à aucun gestionnaire.");
        }

        ContratBail contrat = ContratBail.builder()
                .dateSignature(LocalDate.now())
                .dateDebut(preContrat.getDateDebutPrevu()) 
                .jourEcheanceLoyer(preContrat.getJourEcheancePaiement()) 
                .montantLoyer(preContrat.getMontantLoyer()) 
                .montantCaution(preContrat.getMontantCaution()) 
                .conditions(preContrat.getConditions()) 
                .statutContrat(StatutContrat.ACTIF)
                .preContrat(preContrat)
                .locataire(locataireUser)
                .build();

        contrat = contratRepo.save(contrat);

        String typeBien;
        switch (bien.getTypeBien()) {
            case APPARTEMENT -> typeBien = "Appartement";
            case CHAMBRE -> typeBien = "Chambre";
            case LOCAL -> typeBien = "Local commercial";
            case STUDIO -> typeBien = "Studio";
            case VILLA -> typeBien = "Villa";
            default -> typeBien = "Type de propriété inconnu";
        }
        String libelle = bien.getLibelle();

        byte[] pdfBytes = pdfService.genererContratPdf(contrat, locataireUser, gestionnaireUser, typeBien, libelle);

        String nomFichierUnique = "contrat_bail_definitif_" + contrat.getId();
        String urlCloudinaryPdf = cloudinaryService.uploadPdf(pdfBytes, nomFichierUnique);

        contrat.setCheminPDF(urlCloudinaryPdf);
        contrat = contratRepo.save(contrat);

        return contratMapper.toDTO(contrat);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContratBailResponseDTO> getContratsPourLocataire(Long locataireId, Pageable pageable) {
        if (locataireId == null) {
            throw new IllegalArgumentException("L'ID du locataire ne peut pas être nul.");
        }
        
        return contratRepo.findByLocataireId(locataireId, pageable)
                .map(contratMapper::toDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ContratBailResponseDTO> getContratsPourGestionnaire(Long gestionnaireId, Pageable pageable) {
        if (gestionnaireId == null) {
            throw new IllegalArgumentException("L'ID du gestionnaire ne peut pas être nul.");
        }

        return contratRepo.findByGestionnaireId(gestionnaireId, pageable)
                .map(contratMapper::toDTO);
    }
}
