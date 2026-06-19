package gesimmo.nekaso.dto.ContratDTO;

import lombok.Data;
import java.time.LocalDate;
import gesimmo.nekaso.entity.enums.StatutContrat;

@Data
public class ContratBailResponseDTO {
    private Long id;
    private LocalDate dateSignature;
    private LocalDate dateDebut;
    private Integer jourEcheanceLoyer;
    private Double montantLoyer;
    private Double montantCaution;
    private String conditions;
    private String cheminPDF;
    private StatutContrat statutContrat;
    private Long preContratId;
    private Long locataireId;
}