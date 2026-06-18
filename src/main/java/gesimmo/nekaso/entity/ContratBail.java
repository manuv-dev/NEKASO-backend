package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "date_signature")
    private LocalDate dateSignature;

    @Column(name = "montant_loyer")
    private Double montantLoyer;
    @Column(name = "montant_caution")
    private Double montantCaution;
    @Column(name = "conditions",columnDefinition = "TEXT")
    private String conditions;
    @Column(name = "date_debut")
    private String dateDebut;
    @Column(name = "cheminpdf",columnDefinition = "TEXT")
    private String cheminPDF;

    @OneToOne
    @JoinColumn(name = "demande_location_id")
    private DemandeLocation demandeLocation;
    @OneToOne
    @JoinColumn(name = "paiement_id")   
    private List<Paiement> listePaiement;

    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Locataire locataire;

}
