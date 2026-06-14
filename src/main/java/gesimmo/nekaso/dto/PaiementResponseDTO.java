package gesimmo.nekaso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaiementResponseDTO {
    private Long id;
    private Double montant;
    private String methodePaiement;
    private LocalDate datePaiement;
    private String mois;
    private String reference;
    private Long contratId;
}
