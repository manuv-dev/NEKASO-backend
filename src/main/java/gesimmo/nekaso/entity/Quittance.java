package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "quittance")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Quittance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numero;
    private LocalDate dateEmission = LocalDate.now();
    private String cheminPDF;
    private Double montant;

    @ManyToOne
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;
}
