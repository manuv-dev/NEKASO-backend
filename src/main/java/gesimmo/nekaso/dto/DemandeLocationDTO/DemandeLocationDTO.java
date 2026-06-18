package gesimmo.nekaso.dto.DemandeLocationDTO;

import lombok.*;


@Builder
public record DemandeLocationDTO(
    Long id,
    String statut,
    String dateDemande,
    Long locataireId,
    Long bienId) {
}
