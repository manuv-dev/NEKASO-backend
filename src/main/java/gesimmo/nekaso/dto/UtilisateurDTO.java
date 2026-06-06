package gesimmo.nekaso.dto;

import lombok.Builder;

@Builder
public record UtilisateurDTO(
	 Long id,
	 String nom,
	 String prenom,
	 String telephone
) {}
