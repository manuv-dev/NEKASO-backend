package gesimmo.nekaso.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class RevenueMensuelDTO {
    private String mois;
    private Double encaisse;   
    private Double precedent;  
}
