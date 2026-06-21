package gesimmo.nekaso.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratRequestDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.entity.enums.ClotureVisite;

public interface DemandeVisiteService {
	public DemandeVisiteCreateResponseDTO createDemandeVisite(Long id_Locataire, Long id_Bien);
	Page<DemandeVisite> getAllDemandesVisite(Pageable pageable,String statut,Long id_Locataire);
	Page<BienImmobilier> getBiensDisponibles(Pageable pageable);
	Page<DemandeVisite> getAllDemandesVisiteByGestionnaire(Pageable pageable,String statut);
	DemandeVisiteCreateResponseDTO updateDemandeVisiteStatut(Long id, String statut);
	DemandeVisiteCreateResponseDTO confirmerDemandeVisite(Long id,Long id_bien,Long id_agent);
	DemandeVisite annulerDemandeVisite(Long idDemande);
	DemandeVisiteCreateResponseDTO cloturerVisite(Long idDemande, ClotureVisite choixCloture);
	DemandeVisiteCreateResponseDTO ProposerUnCreneau(Long idDemande, String creneauVisite,Long IdAgent);
	DemandeVisiteCreateResponseDTO accepterCreneau(Long idDemande);
	DemandeVisiteCreateResponseDTO refuserCreneau(Long idDemande);	
	DemandeVisiteCreateResponseDTO proposerUnPreContrat(PreContratRequestDTO preContratDto);
	// DemandeVisite annulerDemandeVisite(Long id);
	// DemandeVisite accepterDemandeVisite(Long id);


}
