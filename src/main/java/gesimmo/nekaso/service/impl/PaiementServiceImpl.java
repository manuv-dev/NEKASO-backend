package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.ContratBail;

import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.entity.PreContrat;
import gesimmo.nekaso.exception.EntityNotFoundException;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.PaiementRepository;
import gesimmo.nekaso.service.PaiementService;
import gesimmo.nekaso.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gesimmo.nekaso.service.CloudinaryService;


import java.time.LocalDate;

import gesimmo.nekaso.mapper.PaiementMapper;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {
    
    private final PaiementRepository paiementRepository;
    private final ContratBailRepository contratBailRepository;
    private final PdfService pdfService;
    private final CloudinaryService cloudinaryService;
    private final PaiementMapper paiementMapper;


   @Override
    @Transactional
    public PaiementDTO CreatePaiement(PaiementDTO dto) {
        
        ContratBail contrat = contratBailRepository.findById(dto.getContratId())
                .orElseThrow(() -> new EntityNotFoundException("Impossible d'enregistrer le paiement : Contrat introuvable avec l'ID : " + dto.getContratId()));

        if (dto.getMethodePaiement() == null) {
            throw new IllegalArgumentException("La méthode de paiement ne doit pas être nulle.");
        }
        if (dto.getMois() == null) {
            throw new IllegalArgumentException("Le mois de paiement ne doit pas être null.");
        }

        PreContrat preContrat = contrat.getPreContrat();
        if (preContrat == null) {
            throw new EntityNotFoundException("Erreur d'intégrité : Aucun pré-contrat n'est rattaché à ce contrat de bail.");
        }

        BienImmobilier bien = preContrat.getDemandeLocation() != null 
                ? preContrat.getDemandeLocation().getBien() 
                : preContrat.getDemandeVisite().getBienImmobilier();

        if (bien == null) {
            throw new EntityNotFoundException("Erreur d'intégrité : Aucun bien immobilier n'est rattaché à ce contrat.");
        }

        Locataire locataireUser = contrat.getLocataire(); // Liaison directe sur ton entité ContratBail
        Gestionnaire gestionnaireUser = bien.getGestionnaire();

        if (locataireUser == null) {
            throw new EntityNotFoundException("Erreur critique : Aucun locataire n'est trouvé pour ce contrat.");
        }
        if (gestionnaireUser == null) {
            throw new EntityNotFoundException("Erreur critique : Aucun gestionnaire n'est associé au bien de ce contrat.");
        }

        Paiement paiement = Paiement.builder()           
                .montant(contrat.getMontantLoyer()) 
                .datePaiement(LocalDate.now())
                .mois(dto.getMois())
                .reference("PAY-" + System.currentTimeMillis()) 
                .methodePaiement(dto.getMethodePaiement())
                .contrat(contrat) 
                .build();
        
        Paiement savedPaiement = paiementRepository.save(paiement);

        String typeBien;
        switch (bien.getTypeBien()) {
            case APPARTEMENT -> typeBien = "Appartement";
            case CHAMBRE -> typeBien = "Chambre";
            case LOCAL -> typeBien = "Local commercial";
            case STUDIO -> typeBien = "Studio";
            default -> typeBien = "Type inconnu";
        }
        String libelle = bien.getLibelle();

        byte[] pdfBytes = pdfService.genererQuittancePdf(savedPaiement, locataireUser, gestionnaireUser, typeBien, libelle);

        String nomFichierUnique = "Quittance_" + savedPaiement.getId() + "_" + savedPaiement.getDatePaiement();
        String urlCloudinaryPdf = cloudinaryService.uploadPdf(pdfBytes, nomFichierUnique);

        savedPaiement.setQuittance(urlCloudinaryPdf);
        Paiement finalPaiement = paiementRepository.save(savedPaiement);

        return paiementMapper.toDTO(finalPaiement);
    }

    @Override
    public Page<PaiementDTO> getPaiementByContratId(Long id, Pageable pageable) {

        if (!contratBailRepository.existsById(id)) {
            throw new RuntimeException("Le contrat n'existe pas");
        }

        Page<Paiement> paiements = paiementRepository.findByContratId(id, pageable);

        return paiements.map(paiementMapper::toDTO);
    }

    }


