package gesimmo.nekaso.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@lombok.Data
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nom;

	@Column(nullable = false)
	private String prenom;

	private String telephone;

	@Column(nullable = false)
	private String motDePasse;

	@Column(nullable = false)
	private String role;

	private LocalDateTime dateCreation;

	private String statut;

	@PrePersist
	public void onCreate() {
		if (dateCreation == null) {
			dateCreation = LocalDateTime.now();
		}
	}
}
