package gesimmo.nekaso.mapper;

import gesimmo.nekaso.dto.ContratDTO.ContratBailResponseDTO;
import gesimmo.nekaso.entity.ContratBail;
import org.springframework.stereotype.Component;

@Component
public class ContratBailMapper {

    public ContratBailResponseDTO toDTO(ContratBail contrat) {
        if (contrat == null) return null;

        ContratBailResponseDTO dto = new ContratBailResponseDTO();
        dto.setId(contrat.getId());
        dto.setDateSignature(contrat.getDateSignature());
        dto.setDateDebut(contrat.getDateDebut());
        dto.setJourEcheanceLoyer(contrat.getJourEcheanceLoyer());
        dto.setMontantLoyer(contrat.getMontantLoyer());
        dto.setMontantCaution(contrat.getMontantCaution());
        dto.setConditions(contrat.getConditions());
        dto.setCheminPDF(contrat.getCheminPDF());
        dto.setStatutContrat(contrat.getStatutContrat());
        
        if (contrat.getPreContrat() != null) {
            dto.setPreContratId(contrat.getPreContrat().getId());
        }
        if (contrat.getLocataire() != null) {
            dto.setLocataireId(contrat.getLocataire().getId());
        }
        return dto;
    }
}