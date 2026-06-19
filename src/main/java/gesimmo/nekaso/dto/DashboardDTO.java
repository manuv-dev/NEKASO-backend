package gesimmo.nekaso.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardDTO {
    private Double revenusMois;         
    private Double variationRevenus;     
    private Double tauxOccupation;       
    private Double loyersEnRetard;      
    private Integer locatairesEnRetard;  
}
