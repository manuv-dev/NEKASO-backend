package gesimmo.nekaso.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.entity.DemandeVisite;

public interface DemandeVisiteService {
	public DemandeVisiteCreateResponseDTO createDemandeVisite(Long id_Locataire, Long id_Bien);
	Page<DemandeVisite> getAllDemandesVisite(Pageable pageable,String statut,Long id_Locataire);


}
