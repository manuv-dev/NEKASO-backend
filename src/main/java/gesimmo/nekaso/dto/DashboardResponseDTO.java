package gesimmo.nekaso.dto;

import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
@Builder
public class DashboardResponseDTO {
    // 1. Les KPIs (Cartes supérieures)
    private Double revenusCeMois;
    private Double evolutionRevenusPourcentage; // ex: +8.2
    
    private Double tauxOccupation;              // ex: 40.0
    private Long totalBiensLoues;
    private Long totalBiensPortefeuille;

    private Double totalLoyersEnRetard;
    private Long nombreLocatairesEnRetard;

    // 2. Évolution du Chiffre d'Affaires Locatif (Bar Chart)
    // Structure : Map<"Mois", Map<"TypeStat", Valeur>> 
    // Exemple : "Jan" -> { "encaisse": 1500000.0, "anneePrecedente": 1800000.0 }
    private Map<String, Map<String, Double>> evolutionChiffreAffaires;

    // 3. Répartition du Portefeuille (Donut Chart)
    // Exemple : "Appartement" -> 1 (20%)
    private Map<String, Long> repartitionPortefeuille;

    // 4. Liste des Loyers en Retard (Section Alertes / Relances)
    private List<LoyerRetardDetailDTO> listeLoyersEnRetard;

    @Data
    @Builder
    public static class LoyerRetardDetailDTO {
        private String locataireNom;
        private String echeanceDate; // Format "YYYY-MM-DD"
        private Double montantDu;
    }
}