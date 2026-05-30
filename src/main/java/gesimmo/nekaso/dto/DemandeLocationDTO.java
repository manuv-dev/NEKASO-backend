package gesimmo.nekaso.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeLocationDTO {
    private Long id;
    private String statut;
    private LocalDateTime dateDemande;
    private Long locataireId;
    private Long bienId;
}
