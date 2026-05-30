package gesimmo.nekaso.mapper;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;

public class ContratMapper {

    public static ContratDTO toDTO(ContratBail contrat) {
        return ContratDTO.builder()
                .id(contrat.getId())
                .dateSignature(contrat.getDateSignature())
                .montantLoyer(contrat.getMontantLoyer())
                .montantCaution(contrat.getMontantCaution())
                .conditions(contrat.getConditions())
                .dateDebut(contrat.getDateDebut())
                .cheminPDF(contrat.getCheminPDF())
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
                .cheminPDF(dto.getCheminPDF())
                .build();
    }
}
