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
public class QuittanceResponseDTO {
    private Long id;
    private String numero;
    private LocalDate dateEmission;
    private String cheminPDF;
    private Double montant;
    private Long paiementId;
}
