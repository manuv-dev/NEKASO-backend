package gesimmo.nekaso.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaiementDTO {
    private Long id;
    private Double montant;
    private String typePaiement;
    private String statut;
    private LocalDate datePaiement;
    private String description;
    private String cheminPDF;
    private Long contratId;
    private Long locataireId;
    private Long bienId;
    private Long demandeLocationId;
}
