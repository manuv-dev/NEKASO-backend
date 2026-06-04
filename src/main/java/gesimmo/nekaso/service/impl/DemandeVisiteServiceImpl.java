package gesimmo.nekaso.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gesimmo.nekaso.dto.DemandeVisiteDTO;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.entity.enums.VisiteStatut;
import gesimmo.nekaso.exception.ResourceNotFoundException;

import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.service.DemandeVisiteService;

@Service
public class DemandeVisiteServiceImpl implements DemandeVisiteService {

	private final DemandeVisiteRepository demandeVisiteRepository;

	public DemandeVisiteServiceImpl(DemandeVisiteRepository demandeVisiteRepository) {
		this.demandeVisiteRepository = demandeVisiteRepository;
	}

	@Override
	public List<DemandeVisiteDTO> getDemandesForGestionnaire() {
		// For now return all demandes. Filtering by gestionnaire requires relation not present.
		List<DemandeVisite> demandes = demandeVisiteRepository.findAll();
		// return demandes.stream().map(DemandeVisiteDTO::fromEntity).toList();
		return null;
	}

	@Override
	@Transactional
	public void approuverVisite(Long id) {
		DemandeVisite demande = demandeVisiteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Demande de visite introuvable"));

		if (demande.getStatut() != null && demande.getStatut() != VisiteStatut.EN_ATTENTE) {
			throw new IllegalStateException("Cette demande de visite a déjà été traitée");
		}

		demande.setStatut(VisiteStatut.CONFIRMEE);
		demandeVisiteRepository.save(demande);
	}

	@Override
	@Transactional
	public void refuserVisite(Long id) {
		DemandeVisite demande = demandeVisiteRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Demande de visite introuvable"));

		if (demande.getStatut() != null && demande.getStatut() != VisiteStatut.EN_ATTENTE) {
			throw new IllegalStateException("Cette demande de visite a déjà été traitée");
		}

		demande.setStatut(VisiteStatut.REFUSEE);
		demandeVisiteRepository.save(demande);
	}

}
