package gesimmo.nekaso.dto.DemandeLocationDTO;

import lombok.*;
import java.time.LocalDateTime;

@Builder
public record DemandeLocationDTO(
    Long id,
    String statut,
    String dateDemande,
    Long locataireId,
    Long bienId) {
}
