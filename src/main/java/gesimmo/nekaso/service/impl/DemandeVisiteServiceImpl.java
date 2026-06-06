package gesimmo.nekaso.service.impl;

import java.util.List;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.entity.DemandeVisite;

import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.service.DemandeVisiteService;

@Service
public class DemandeVisiteServiceImpl implements DemandeVisiteService {

	private final DemandeVisiteRepository demandeVisiteRepository;

	public DemandeVisiteServiceImpl(DemandeVisiteRepository demandeVisiteRepository) {
		this.demandeVisiteRepository = demandeVisiteRepository;
	}

	// @Override
	// public Page<DemandeVisiteResponseDTO> getAllDemandesVisite(Pageable pageable) {
		
		
	// }

	@Override
	public Page<DemandeVisite> getDemandesVisiteByLocataireId(Long locataireId, Pageable pageable) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getDemandesVisiteByLocataireId'");
	}

	@Override
	public Page<DemandeVisite> searchDemandesVisiteByLocataire(String nom, Pageable pageable,String prenom) {
		 if (nom == null) {
            nom = "";
        }
        if (prenom == null) {
            prenom = "";
        }

        nom = nom.trim();
        prenom = prenom.trim();	
	
	Page<DemandeVisite> demandes ;
	
	if (nom.isEmpty() && prenom.isEmpty()) {
			return demandes=demandeVisiteRepository.findAll(pageable);
		}

			return demandes=demandeVisiteRepository.rechercherParNomEtPrenomLocataire(nom, prenom, pageable);
		

	

}

}