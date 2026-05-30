package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "paiement")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Paiement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;
    private LocalDate datePaiement;
    private String mois;
    private String methodePaiement;
    private String reference;

    @ManyToOne
    @JoinColumn(name = "contrat_id")
    private ContratBail contrat;
}
