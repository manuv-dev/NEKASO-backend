package gesimmo.nekaso.dto.PreContratDTO;

import lombok.Data;
import java.time.LocalDate;

@Data
public class PreContratUpdateRequestDTO {
    private String conditions;
    private Integer jourEcheancePaiement;
    private LocalDate dateDebutPrevu;
}