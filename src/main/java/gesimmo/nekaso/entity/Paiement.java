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

    @Column(nullable = false)
    private Double montant;

    @Column (nullable = false)
    @Enumerated(EnumType.STRING)
    private String MethodePaiement;

    private LocalDate datePaiement = LocalDate.now();

    @Column(nullable = false)
    private String mois;

    @Column(nullable = false)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "contrat_id")
    private ContratBail contrat;

}
