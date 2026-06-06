package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;

public interface DemandeVisiteService {
	public DemandeVisiteCreateResponseDTO createDemandeVisite(Long id_Locataire, Long id_Bien);
}
