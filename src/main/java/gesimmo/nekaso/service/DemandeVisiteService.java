package gesimmo.nekaso.service;

import java.util.List;

import gesimmo.nekaso.dto.DemandeVisiteDTO;

public interface DemandeVisiteService {
	List<DemandeVisiteDTO> getDemandesForGestionnaire();

	void approuverVisite(Long id);

	void refuserVisite(Long id);
}
