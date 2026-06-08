package gesimmo.nekaso.service.impl;


import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
	@Transactional
	public DemandeVisiteCreateResponseDTO createDemandeVisite(Long id_Locataire, Long id_Bien) {

		boolean existeDeja = demandeVisiteRepository.existsByLocataireIdAndBienImmobilierIdAndStatut(
        id_Locataire, 
        id_Bien, 
        VisiteStatut.EN_ATTENTE
    );

    if (existeDeja) {
        throw new IllegalStateException("Vous avez déjà une demande en attente pour ce bien.");
    }
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

	@Override
	public Page<DemandeVisite> getAllDemandesVisite(Pageable pageable, String statut, Long id_Locataire) {
		if(statut==null){
			statut="";
		}
		
		Page<DemandeVisite> demandesPage ;
		if(statut.isEmpty()){
			demandesPage = demandeVisiteRepository.findByLocataireId(id_Locataire, pageable);
		} else {
			 demandesPage = demandeVisiteRepository.findByStatutAndLocataireId(VisiteStatut.valueOf(statut.toUpperCase()), id_Locataire, pageable);
		}
		return demandesPage;
		
	}

	public DemandeVisite annulerDemandeVisite(Long id_Demande) {
		DemandeVisite demandeVisite = demandeVisiteRepository.findById(id_Demande)
				.orElseThrow(() -> new ResourceNotFoundException("La demande de visite avec l'ID " + id_Demande + " n'a pas été trouvée"));
		demandeVisite.setStatut(VisiteStatut.ANNULEE);
		return demandeVisiteRepository.save(demandeVisite);
	}

	// public Page<BienImmobilier>

}
