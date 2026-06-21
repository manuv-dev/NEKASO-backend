package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import gesimmo.nekaso.entity.enums.StatutPreContrat;

@Entity
@Table(name = "pre_contrat")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PreContrat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date_creation", nullable = false)
    private LocalDate dateCreation;

    @Column(name = "date_debut_prevu", nullable = false)
    private LocalDate dateDebutPrevu; 

    @Column(name = "jour_echeance_paiement", nullable = false)
    private Integer jourEcheancePaiement; 

    @Column(name = "montant_loyer", nullable = false)
    private Double montantLoyer;

    @Column(name = "montant_caution", nullable = false)
    private Double montantCaution;

    @Column(name = "conditions", columnDefinition = "TEXT")
    private String conditions;

    @Enumerated(EnumType.STRING)
    @Column(name = "statut_pre_contrat", nullable = false)
    private StatutPreContrat statutPreContrat;

    @ManyToOne
    @JoinColumn(name = "demande_location_id")
    private DemandeLocation demandeLocation;

    @ManyToOne
    @JoinColumn(name = "demande_visite_id")
    private DemandeVisite demandeVisite;

    @ManyToOne
    @JoinColumn(name = "locataire_id", nullable = false)
    private Locataire locataire;

}