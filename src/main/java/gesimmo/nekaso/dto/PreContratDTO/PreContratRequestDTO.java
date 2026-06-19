package gesimmo.nekaso.dto.PreContratDTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PreContratRequestDTO {
   private LocalDate dateDebutPrevu;
    private Integer jourEcheancePaiement;
    private String conditions;
    private Long demandeLocationId; 
    private Long demandeVisiteId;
}