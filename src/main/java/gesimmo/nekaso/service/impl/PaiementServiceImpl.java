package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.PaiementRepository;
import gesimmo.nekaso.service.PaiementService;
import gesimmo.nekaso.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import gesimmo.nekaso.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gesimmo.nekaso.service.CloudinaryService;


import java.time.LocalDate;
import java.util.Optional;
import gesimmo.nekaso.mapper.PaiementMapper;

@Service
@RequiredArgsConstructor
public class PaiementServiceImpl implements PaiementService {
    
    private final PaiementRepository paiementRepository;
    private final ContratBailRepository contratBailRepository;
    private final PdfService pdfService;
    private final CloudinaryService cloudinaryService;
    private final PaiementMapper paiementMapper;


    @Transactional
    public PaiementDTO CreatePaiement(PaiementDTO dto) {
        
            ContratBail contrat = contratBailRepository.findById(dto.getContratId())
                    .orElseThrow(() -> new RuntimeException("Contrat introuvable."));

            if (dto.getMethodePaiement()== null){
                 throw new RuntimeException("La méthode de paiement ne doit pas être null.");
            }
            if (dto.getMois()== null){
                 throw new RuntimeException("Le mois de paiement ne doit pas être null.");
            }
            Paiement paiement = Paiement.builder()           
                    .montant(contrat.getDemandeLocation().getBien().getLoyer())
                    .datePaiement(LocalDate.now())
                    .mois(dto.getMois())
                    .reference("PAY-" + System.currentTimeMillis()) 
                    .methodePaiement(dto.getMethodePaiement())
                    .build();
            
            paiement.setContrat(contrat);
            Paiement savedPaiement = paiementRepository.save(paiement);

            String typeBien;
            switch (paiement.getContrat().getDemandeLocation().getBien().getTypeBien()) {
                case APPARTEMENT -> typeBien = "Appartement";
                case CHAMBRE -> typeBien = "Chambre";
                case LOCAL -> typeBien = "Local commercial";
                case STUDIO -> typeBien = "Studio";
                default -> typeBien = "Type inconnu";
            }

            String libelle = paiement.getContrat().getDemandeLocation().getBien().getLibelle() ;

            User locataireUser = paiement.getContrat().getDemandeLocation().getLocataire().getUser();
            User gestionnaireUser = paiement.getContrat().getDemandeLocation().getBien().getGestionnaire().getUser();
            
            byte[] pdfBytes = pdfService.genererQuittancePdf(paiement, locataireUser, gestionnaireUser, typeBien, libelle);

            // 7. Envoi du fichier sur Cloudinary et récupération du lien URL public HTTPS
            String nomFichierUnique = "Quittance_" + paiement.getId() + paiement.getDatePaiement();
            String urlCloudinaryPdf = cloudinaryService.uploadPdf(pdfBytes, nomFichierUnique);

            savedPaiement.setQuittance(urlCloudinaryPdf);

            paiement = paiementRepository.save(savedPaiement);

            return paiementMapper.toDTO(paiement);

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


