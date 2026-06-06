package gesimmo.nekaso.entity;

import java.time.LocalDate;

import gesimmo.nekaso.entity.enums.VisiteStatut;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "demande_visite")
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandeVisite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Enumerated(EnumType.STRING)
	private VisiteStatut statut;

	private LocalDate dateCreation;

	@ManyToOne
	@JoinColumn(name = "locataire_id")
	private Locataire locataire;

	@ManyToOne
	@JoinColumn(name = "bien_id")
	private BienImmobilier bienImmobilier;
}
