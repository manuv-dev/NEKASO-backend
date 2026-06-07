package gesimmo.nekaso.dto.DemandeVisiteDTO;



import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOLoc;
import lombok.Builder;

@Builder
public record DemandeVisiteDTOList(
	 Long id,
	 String statut,
	 String dateCreation,
	 Long id_Locataire,
	 BienImmobilierResponseDTOLoc bien
) {
}