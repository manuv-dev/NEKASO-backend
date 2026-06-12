package gesimmo.nekaso.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gesimmo.nekaso.entity.enums.StatutDemande;   

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

    @Enumerated(EnumType.STRING)
    private StatutDemande  statut;
   
    @Column(name = "datedemande")
    private LocalDateTime dateDemande;

    @ManyToOne
    @JoinColumn(name = "locataire_id")
    private Locataire locataire;

    @ManyToOne
    @JoinColumn(name = "bien_id")
    private BienImmobilier bien;
}
