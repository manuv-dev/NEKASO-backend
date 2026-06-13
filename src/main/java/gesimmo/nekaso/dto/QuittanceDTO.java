package gesimmo.nekaso.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuittanceDTO {
    private Long id;
    private Long paiementId;
    private String numero;
    private LocalDate dateEmission;
    private String cheminPDF;
    private Double montant;
}
