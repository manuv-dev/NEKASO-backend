package gesimmo.nekaso.entity;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.MethodePaiement;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "paiement")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Paiement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Double montant;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private MethodePaiement methodePaiement;

    private LocalDate datePaiement = LocalDate.now();

    @Column(nullable = false)
    private String mois;

    @Column(nullable = false)
    private String reference;

    private String statut;
    private String description;
    private String cheminPDF;

    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Locataire locataire;

    @ManyToOne
    @JoinColumn(name = "bien_id")
    private BienImmobilier bien;

    @ManyToOne
    @JoinColumn(name = "demande_location_id")
    private DemandeLocation demandeLocation;

    @ManyToOne
    @JoinColumn(name = "contrat_id")
    private ContratBail contrat;

}