package gesimmo.nekaso.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RepartitionBienDTO {
    private String typeBien;
    private Long nombre;       // Nombre de biens
    private Double pourcentage; // % du portefeuille
}
