package gesimmo.nekaso.dto.DemandeVisiteDTO;




import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import lombok.Builder;

@Builder
public record DemandeVisiteDTOList(
	 Long id,
	 String statut,
	 String nomLocataire,
	 String prenomLocataire,
	 String telephoneLocataire,
	 String dateCreation,
	 Long id_Locataire,
	 BienImmobilierResponseDTOGes bien

) {
}