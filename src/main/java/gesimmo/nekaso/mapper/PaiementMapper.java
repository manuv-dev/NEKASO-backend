package gesimmo.nekaso.mapper;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.entity.Paiement;

@Component
public class PaiementMapper {


    public PaiementDTO toDTO(Paiement paiement) {
        return PaiementDTO.builder()
                .id(paiement.getId())
                .montant(paiement.getMontant())
                .datePaiement(paiement.getDatePaiement())
                .mois(paiement.getMois())
                .reference(paiement.getReference())
                .methodePaiement(paiement.getMethodePaiement())
                .contratId(paiement.getContrat().getId())
                .quittance(paiement.getQuittance())
                .build();
    }


    public static Paiement toEntity(PaiementDTO dto) {
        return Paiement.builder()
                .id(dto.getId())
                .montant(dto.getMontant())
                .datePaiement(dto.getDatePaiement())
                .mois(dto.getMois())
                .reference(dto.getReference())
                .methodePaiement(dto.getMethodePaiement())
                .quittance(dto.getQuittance())
                .build();
    }
}
