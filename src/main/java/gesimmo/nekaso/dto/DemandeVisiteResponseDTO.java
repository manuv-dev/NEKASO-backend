package gesimmo.nekaso.dto;


import lombok.Builder;


@Builder
public record DemandeVisiteResponseDTO(
    Long id,
    String statut,
    String dateCreation,
    UtilisateurDTO locataire,
	 BienImmobilierResponseDTO bien
) {
}
	

