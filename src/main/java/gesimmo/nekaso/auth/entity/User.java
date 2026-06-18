package gesimmo.nekaso.auth.entity;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;

import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@SuperBuilder
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String nom;

	@Column(nullable = false)
	private String prenom;
	@Column(nullable = false, unique = true)
	private String telephone;

	@Column(name = "motdepasse", nullable = false)
	private String motDePasse;

	 @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn (name = "role_id")
    )
    // private List<Role> roles;
    protected Set<Role> roles=new HashSet<>();

	private LocalDateTime dateCreation;

	private String statut;

	@PrePersist
	public void onCreate() {
		if (dateCreation == null) {
			dateCreation = LocalDateTime.now();
		}
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return roles.stream().
		map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRole().name()))
		.toList();
	}

	@Override
	public @Nullable String getPassword() {
		return motDePasse;
	}

	@Override
	public String getUsername() {
	
		return telephone;
	}
}
