package gesimmo.nekaso.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaiementDTO {
    private Long id;
    private Double montant;
    private LocalDate datePaiement;
    private String mois;
    private String reference;
    private String MethodePaiement;
    private Long contratId;
}
