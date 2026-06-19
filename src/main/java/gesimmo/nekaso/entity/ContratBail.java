package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;
import gesimmo.nekaso.entity.enums.StatutContrat;

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

    @Column(name = "date_signature", nullable = false)
    private LocalDate dateSignature;

    @Column(name = "date_debut", nullable = false)
    private LocalDate dateDebut; 

    @Column(name = "jour_echeance_loyer", nullable = false)
    private Integer jourEcheanceLoyer; 

    @Column(name = "montant_loyer", nullable = false)
    private Double montantLoyer;

    @Column(name = "montant_caution", nullable = false)
    private Double montantCaution;

    @Column(name = "conditions", columnDefinition = "TEXT")
    private String conditions;

    @Column(name = "cheminpdf", columnDefinition = "TEXT")
    private String cheminPDF; 
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private StatutContrat statutContrat;

    @OneToOne
    @JoinColumn(name = "pre_contrat_id", nullable = false)
    private PreContrat preContrat;

    @ManyToOne
    @JoinColumn(name = "locataire_id", nullable = false)
    private Locataire locataire;

    @OneToMany(mappedBy = "contrat", cascade = CascadeType.ALL)   
    private List<Paiement> listePaiement;
}