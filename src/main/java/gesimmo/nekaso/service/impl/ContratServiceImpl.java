package gesimmo.nekaso.service.impl;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.repository.ContratBailRepository;
import gesimmo.nekaso.repository.DemandeLocationRepository;
import gesimmo.nekaso.service.ContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContratServiceImpl implements ContratService {

    private final ContratBailRepository contratRepo;
    private final DemandeLocationRepository demandeRepo;

    @Override
    public ContratBail creerContrat(ContratDTO dto) {
        DemandeLocation demande = demandeRepo.findById(dto.getDemandeLocationId())
                .orElseThrow(() -> new RuntimeException("Demande introuvable"));

        ContratBail contrat = ContratBail.builder()
                .dateSignature(dto.getDateSignature())
                .dateDebut(dto.getDateDebut())
                .montantLoyer(dto.getMontantLoyer())
                .montantCaution(dto.getMontantCaution())
                .conditions(dto.getConditions())
                .cheminPDF(dto.getCheminPDF())
                .demandeLocation(demande)
                .build();

        return contratRepo.save(contrat);
    }

    @Override
    public List<ContratBail> getContratsParLocataire(Long locataireId) {
        return contratRepo.findByDemandeLocation_Locataire_Id(locataireId);
    }

    @Override
    public List<ContratBail> getContratsParBien(Long bienId) {
        return contratRepo.findByDemandeLocation_Bien_Id(bienId);
    }

    @Override
    public List<ContratBail> getContratsParGestionnaire(Long gestionnaireId) {
        return contratRepo.findByDemandeLocation_Bien_Gestionnaire_Id(gestionnaireId);
    }
}
