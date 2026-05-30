package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "quittance")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class Quittance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateEmission;
    private String cheminPDF;

    @ManyToOne
    @JoinColumn(name = "paiement_id")
    private Paiement paiement;
}
