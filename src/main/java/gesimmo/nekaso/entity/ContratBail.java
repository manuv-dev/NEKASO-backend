package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "contrat_bail")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ContratBail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateSignature;
    private Double montantLoyer;
    private Double montantCaution;
    private String conditions;
    private LocalDate dateDebut;
    private String cheminPDF;

    @OneToOne
    @JoinColumn(name = "demande_location_id")
    private DemandeLocation demandeLocation;
}
