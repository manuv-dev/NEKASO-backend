package gesimmo.nekaso.dto.DemandeVisiteDTO;
import gesimmo.nekaso.dto.UtilisateurDTO;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import lombok.Builder;


@Builder
public record DemandeVisiteDTO(
	Long id,
	String statut,
	String dateCreation,
	UtilisateurDTO locataire,
	BienImmobilierResponseDTOGes bien) {
} 