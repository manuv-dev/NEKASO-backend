package gesimmo.nekaso.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RevenueMensuelDTO {
    private String mois;
    private Double encaisse;   // Revenus encaissés
    private Double precedent;  // Revenus de l'année précédente
}
