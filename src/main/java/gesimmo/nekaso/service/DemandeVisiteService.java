package gesimmo.nekaso.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gesimmo.nekaso.entity.DemandeVisite;

public interface DemandeVisiteService {
	// Page<DemandeVisiteResponseDTO> getAllDemandesVisite(Pageable pageable);

	Page<DemandeVisite> getDemandesVisiteByLocataireId(Long locataireId, Pageable pageable);
	Page<DemandeVisite> searchDemandesVisiteByLocataire(String nom, Pageable pageable,String prenom);

	// void approuverVisite(Long id);

	// void refuserVisite(Long id);
}
