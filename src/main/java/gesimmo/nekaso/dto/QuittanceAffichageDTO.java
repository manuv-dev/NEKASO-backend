package gesimmo.nekaso.dto;

import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuittanceAffichageDTO {
    private Long id;
    private String numero;
    private Double montantPaye;
    private String periode;
    private LocalDate datePaiement;
    private LocalDate dateEmission;
    private String cheminPDF;

    // Bien associé
    private Long bienId;
    private String bienAdresse;
    private String bienType;
    private Double bienLoyer;

    // Contrat associé
    private Long contratId;
    private LocalDate contratDateDebut;
    private Double contratMontantLoyer;
}
