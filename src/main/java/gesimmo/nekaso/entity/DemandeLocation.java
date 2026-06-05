package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "demande_location")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DemandeLocation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String statut;

    @Builder.Default
    private LocalDateTime dateDemande = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Locataire locataire;

    @ManyToOne
    @JoinColumn(name = "bien_id")
    private BienImmobilier bien;
}
