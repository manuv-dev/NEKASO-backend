package gesimmo.nekaso.dto;

import java.time.LocalDate;

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
	

