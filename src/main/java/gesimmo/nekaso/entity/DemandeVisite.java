package gesimmo.nekaso.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import gesimmo.nekaso.entity.enums.VisiteStatut;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "demande_visite")
@lombok.Data
public class DemandeVisite {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50)
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
