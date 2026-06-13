package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

import gesimmo.nekaso.entity.enums.MethodePaiement;
import gesimmo.nekaso.entity.enums.Mois;

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
    private MethodePaiement methodePaiement;

    private LocalDate datePaiement;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Mois mois;

    @Column(nullable = false)
    private String reference;

    @ManyToOne
    @JoinColumn(name = "contrat_id")
    private ContratBail contrat;
    @OneToOne(mappedBy = "paiement", cascade = CascadeType.ALL)
    private Quittance quittance;

}
