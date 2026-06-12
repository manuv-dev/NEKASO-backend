package gesimmo.nekaso.entity;

import java.time.LocalDateTime;

import gesimmo.nekaso.entity.enums.Role;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@lombok.Data
@SuperBuilder
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nom;

	@Column(nullable = false)
	private String prenom;

	private String telephone;

	@Column(name = "motdepasse", nullable = false)
	private String motDePasse;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private Role role;

	private LocalDateTime dateCreation;

	private String statut;

	@PrePersist
	public void onCreate() {
		if (dateCreation == null) {
			dateCreation = LocalDateTime.now();
		}
	}
}
