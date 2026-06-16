package gesimmo.nekaso.mapper;

import gesimmo.nekaso.dto.QuittanceDTO;
import gesimmo.nekaso.entity.Quittance;

public class QuittanceMapper {

    //toDTO
    public static QuittanceDTO toDTO(Quittance quittance) {
        return QuittanceDTO.builder()
                .id(quittance.getId())
                .dateEmission(quittance.getDateEmission())
                .cheminPDF(quittance.getCheminPDF())
                .paiementId(quittance.getPaiement().getId())
                .build();
    }

    public static Quittance toEntity(QuittanceDTO dto) {
        return Quittance.builder()
                .id(dto.getId())
                .dateEmission(dto.getDateEmission())
                .cheminPDF(dto.getCheminPDF())
                .build();
    }
    
}
