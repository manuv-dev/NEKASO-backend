package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

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
    @Column(nullable = false)
    private LocalDateTime dateSignature;
    @Column(nullable = false)
    private Double montantLoyer;
    @Column(nullable = false)
    private Double montantCaution;
    @Column(nullable = false)
    private String conditions;

    @Column(nullable = false)
    private LocalDateTime dateDebut;
    @Column(nullable = false)
    private String cheminPDF;

    @OneToOne
    @JoinColumn(name = "demande_location_id")
    private DemandeLocation demandeLocation;
}
