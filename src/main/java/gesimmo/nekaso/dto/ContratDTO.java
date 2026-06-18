package gesimmo.nekaso.dto;

import lombok.*;
import java.time.LocalDate;

import gesimmo.nekaso.entity.enums.StatutContrat;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratDTO {
    private Long id;
    private LocalDate dateSignature;
    private Double montantLoyer;
    private Double montantCaution;
    private String conditions;
    private String dateDebut;
    private String cheminPDF;
    private StatutContrat statutContrat;
    private Long demandeLocationId;
}
