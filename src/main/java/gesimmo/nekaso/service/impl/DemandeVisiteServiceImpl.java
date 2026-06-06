package gesimmo.nekaso.service.impl;


import org.springframework.stereotype.Service;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.mapper.DemandeVisiteMapper;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.VisiteStatut;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.service.DemandeVisiteService;

@Service
public class DemandeVisiteServiceImpl implements DemandeVisiteService {
	private final DemandeVisiteRepository demandeVisiteRepository;
	private final LocataireRepository locataireRepository;
	private final BienImmobilierRepository bienRepository;
	private final DemandeVisiteMapper demandeVisiteMapper;

	public DemandeVisiteServiceImpl(
		DemandeVisiteRepository demandeVisiteRepository,
		LocataireRepository locataireRepository,
		BienImmobilierRepository bienRepository,
		DemandeVisiteMapper demandeVisiteMapper) 

		{this.demandeVisiteRepository = demandeVisiteRepository;
		this.locataireRepository = locataireRepository;
		this.bienRepository = bienRepository;
		this.demandeVisiteMapper = demandeVisiteMapper;}

	@Override
	public DemandeVisiteCreateResponseDTO createDemandeVisite(Long id_Locataire, Long id_Bien) {

		Locataire locataire = locataireRepository.findById(id_Locataire)
				.orElseThrow(() -> new ResourceNotFoundException("Le locataire avec l'ID " + id_Locataire + " n'a pas été trouvé"));
		BienImmobilier bien = bienRepository.findById(id_Bien)
				.orElseThrow(() -> new ResourceNotFoundException("Le bien immobilier avec l'ID " + id_Bien + " n'a pas été trouvé"));

		 DemandeVisite demandeVisite = DemandeVisite.builder()
				.locataire(locataire)
				.bienImmobilier(bien)
				.dateCreation(java.time.LocalDate.now())
				.statut(VisiteStatut.EN_ATTENTE)
				.build();
		demandeVisiteRepository.save(demandeVisite);

		return demandeVisiteMapper.toDto(demandeVisite);
		
		
	}

	

}
