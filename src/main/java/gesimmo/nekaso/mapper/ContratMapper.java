package gesimmo.nekaso.mapper;

import org.hibernate.validator.constraints.CodePointLength;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.enums.StatutContrat;

@Component
public class ContratMapper {

    public ContratDTO toDTO(ContratBail contrat) {
        return ContratDTO.builder()
                .id(contrat.getId())
                .dateSignature(contrat.getDateSignature())
                .montantLoyer(contrat.getMontantLoyer())
                .montantCaution(contrat.getMontantCaution())
                .conditions(contrat.getConditions())
                .dateDebut(contrat.getDateDebut())
                .cheminPDF(contrat.getCheminPDF())
                .statutContrat(contrat.getStatutContrat())
                .demandeLocationId(contrat.getDemandeLocation().getId())
                .build();
    }
    public static ContratBail toEntity(ContratDTO dto) {
        return ContratBail.builder()
                .id(dto.getId())
                .dateSignature(dto.getDateSignature())
                .montantLoyer(dto.getMontantLoyer())
                .montantCaution(dto.getMontantCaution())
                .conditions(dto.getConditions())
                .dateDebut(dto.getDateDebut())
                .statutContrat(dto.getStatutContrat())
                .cheminPDF(dto.getCheminPDF())
                .build();
    }
}
