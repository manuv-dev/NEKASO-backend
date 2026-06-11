package gesimmo.nekaso.dto;

import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class DashboardDTO {
    private Double revenusMois;          // Revenus du mois en cours
    private Double variationRevenus;     // Variation par rapport au mois précédent (%)
    private Double tauxOccupation;       // % de biens loués
    private Double loyersEnRetard;       // Montant total des loyers en retard
    private Integer locatairesEnRetard;  // Nombre de locataires en retard
}
