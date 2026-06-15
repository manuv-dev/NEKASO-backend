package gesimmo.nekaso.dto;

import java.util.Set;

import lombok.Data;

@Data
public class UtilisateurDTO {
	private Long id;
	private String username;
	private String nom;
	private String prenom;
	private String telephone;
	private Set<String> roles;
}
