package gesimmo.nekaso.dto.DemandeVisiteDTO;

import java.time.LocalDate;

import gesimmo.nekaso.dto.UtilisateurDTO;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTO;
import lombok.Data;

@Data
public class DemandeVisiteDTO {
	private Long id;
	private String statut;
	private LocalDate dateCreation;
	private UtilisateurDTO locataire;
	private BienImmobilierResponseDTO bien;
}
