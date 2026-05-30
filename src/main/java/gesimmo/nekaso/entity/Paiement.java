package gesimmo.nekaso.entity;

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

    private Double montant;
    private String typePaiement;
    private String statut;
    private LocalDate datePaiement = LocalDate.now();
    private String mois;
    private String description;
    private String cheminPDF;

    @ManyToOne
    @JoinColumn(name = "contrat_id")
    private ContratBail contrat;

    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Locataire locataire;

    @ManyToOne
    @JoinColumn(name = "bien_id")
    private BienImmobilier bien;

    @ManyToOne
    @JoinColumn(name = "demande_location_id")
    private DemandeLocation demandeLocation;
}
