package gesimmo.nekaso.mapper;

import gesimmo.nekaso.dto.PreContratDTO.*;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.PreContrat;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import gesimmo.nekaso.entity.enums.StatutPreContrat;

@Component
public class PreContratMapper {

    public PreContrat toEntity(PreContratRequestDTO dto) {
        if (dto == null) return null;
        
        PreContrat preContrat = new PreContrat();
        preContrat.setDateCreation(LocalDate.now());
        preContrat.setDateDebutPrevu(dto.getDateDebutPrevu());
        preContrat.setJourEcheancePaiement(dto.getJourEcheancePaiement());
        preContrat.setConditions(dto.getConditions());
        preContrat.setStatutPreContrat(StatutPreContrat.EN_ATTENTE); 

        return preContrat;
    }

    public PreContratResponseDTO toResponseDTO(PreContrat preContrat) {
        if (preContrat == null) return null;

        PreContratResponseDTO dto = new PreContratResponseDTO();
        dto.setId(preContrat.getId());
        dto.setDateCreation(preContrat.getDateCreation());
        dto.setDateDebutPrevu(preContrat.getDateDebutPrevu());
        dto.setJourEcheancePaiement(preContrat.getJourEcheancePaiement());
        dto.setMontantLoyer(preContrat.getMontantLoyer());
        dto.setMontantCaution(preContrat.getMontantCaution());
        dto.setConditions(preContrat.getConditions());
        dto.setStatutPreContrat(preContrat.getStatutPreContrat());

        if (preContrat.getLocataire() != null) {
            dto.setLocataireId(preContrat.getLocataire().getId());
            dto.setLocataireNom(preContrat.getLocataire().getNom());
            dto.setLocatairePrenom(preContrat.getLocataire().getPrenom());
            dto.setLocataireTelephone(preContrat.getLocataire().getTelephone());
        }

        BienImmobilier bien = null;

        if (preContrat.getDemandeLocation() != null) {
            bien = preContrat.getDemandeLocation().getBien();
        } 
        if (bien == null && preContrat.getDemandeVisite() != null) {
            bien = preContrat.getDemandeVisite().getBienImmobilier();
        }

        if (bien != null) {
            dto.setBienImmobilierId(bien.getId());
            dto.setBienLibelle(bien.getLibelle());
            
            if (bien.getGestionnaire() != null) {
                dto.setGestionnaireId(bien.getGestionnaire().getId());
                dto.setGestionnaireNom(bien.getGestionnaire().getNom());
                dto.setGestionnairePrenom(bien.getGestionnaire().getPrenom());
                dto.setGestionnaireTelephone(bien.getGestionnaire().getTelephone());
            }
        }

        return dto;
    }
}