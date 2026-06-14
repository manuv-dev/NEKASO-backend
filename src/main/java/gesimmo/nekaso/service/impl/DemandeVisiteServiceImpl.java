package gesimmo.nekaso.service.impl;


import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.mapper.DemandeVisiteMapper;
import gesimmo.nekaso.entity.Locataire;

import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.VisiteStatut;
import gesimmo.nekaso.exception.BienNonDisponibleException;
import gesimmo.nekaso.exception.EntityExistException;
import gesimmo.nekaso.exception.EntityNotFoundException;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.service.DemandeVisiteService;
import java.util.List;

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

			Locataire locataire = locataireRepository.findById(id_Locataire)
				.orElseThrow(() -> new EntityNotFoundException("Le locataire avec l'ID " + id_Locataire + " n'a pas été trouvé"));
		BienImmobilier bien = bienRepository.findById(id_Bien)
				.orElseThrow(() -> new EntityNotFoundException("Le bien immobilier avec l'ID " + id_Bien + " n'a pas été trouvé"));

		List<VisiteStatut> statutsBloquants = List.of(
    VisiteStatut.EN_ATTENTE, 
    VisiteStatut.ANNULEE,
	VisiteStatut.CONFIRMEE,
	VisiteStatut.REFUSEE // ou PROGRAMMEE selon ton énumération
);
		boolean existeDeja = demandeVisiteRepository.existsByLocataireIdAndBienImmobilierIdAndStatutIn(
        id_Locataire, 
        id_Bien, 
        statutsBloquants
    );
	

    if (existeDeja) {
        throw new EntityExistException("Vous avez déjà une demande en attente pour ce bien.");
    }
	 if(!bien.getStatutBien().equals(StatutBien.DISPONIBLE)) {
		throw new BienNonDisponibleException("Ce bien n'est pas disponible pour la visite.");
	}

	
		 DemandeVisite demandeVisite = DemandeVisite.builder()
				.locataire(locataire)
				.bienImmobilier(bien)
				.dateCreation(LocalDateTime.now())
				.statut(VisiteStatut.EN_ATTENTE)
				.build();
		demandeVisiteRepository.save(demandeVisite);

		return demandeVisiteMapper.toDto(demandeVisite);
		
		
	}

@Override
public Page<DemandeVisite> getAllDemandesVisite(Pageable pageable, String statut, Long id_Locataire) {
    Page<DemandeVisite> demandesPage;

    if (statut == null || statut.isBlank()) {
        demandesPage = demandeVisiteRepository.findByLocataireId(id_Locataire, pageable);
    } else {
        demandesPage = demandeVisiteRepository.findByStatutAndLocataireId(
            VisiteStatut.valueOf(statut.toUpperCase()), id_Locataire, pageable
        );
    }

    // On vérifie si la page est vide APRES avoir fait la requête
    if (!demandesPage.hasContent()) {
        throw new EntityNotFoundException("Aucune demande trouvée pour ce locataire (ou ce statut).");
    }

    return demandesPage;
}
// @Override
	public DemandeVisite annulerDemandeVisite(Long id_Demande) {
		DemandeVisite demandeVisite = demandeVisiteRepository.findById(id_Demande)
				.orElseThrow(() -> new EntityNotFoundException("La demande de visite avec l'ID " + id_Demande + " n'a pas été trouvée"));
		demandeVisite.setStatut(VisiteStatut.ANNULEE);
		return demandeVisiteRepository.save(demandeVisite);
	}

@Override
public Page<BienImmobilier> getBiensDisponibles(Pageable pageable) {
    // 1. On récupère la page directement
    Page<BienImmobilier> page = bienRepository.findByStatutBien(StatutBien.DISPONIBLE, pageable);

    // 2. On vérifie le contenu
    if (!page.hasContent()) {
        throw new EntityNotFoundException("Aucun bien immobilier disponible trouvé.");
    }

    // 3. On retourne le résultat
    return page;
}
@Override
public Page<DemandeVisite> getAllDemandesVisiteByGestionnaire(Pageable pageable, String statut) {
	Page<DemandeVisite> demandesPage;

	if (statut == null || statut.isBlank()) {
		demandesPage = demandeVisiteRepository.findAll(pageable);
		System.out.println("Statut non fourni, récupération de toutes les demandes de visite.");
	} else {
		demandesPage = demandeVisiteRepository.findByStatut(VisiteStatut.valueOf(statut.toUpperCase()), pageable);
		System.out.println("Récupération des demandes de visite avec le statut : " + statut);
	}

	if (!demandesPage.hasContent()) {
		System.out.println("Aucune demande trouvée pour ce gestionnaire (ou ce statut).");
		throw new EntityNotFoundException("Aucune demande trouvée pour ce gestionnaire (ou ce statut).");
		
	}

	return demandesPage;

}

@Override
public DemandeVisiteCreateResponseDTO updateDemandeVisiteStatut(Long id, String statut) {
	
	DemandeVisite demandeVisite = demandeVisiteRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("La demande de visite avec l'ID " + id + " n'a pas été trouvée"));

	// Vérification si le statut fourni est valide
	VisiteStatut nouveauStatut;
	try {
		nouveauStatut = VisiteStatut.valueOf(statut.toUpperCase());
	} catch (IllegalArgumentException e) {
		throw new IllegalArgumentException("Statut invalide : " + statut);
	}

	demandeVisite.setStatut(nouveauStatut);
	DemandeVisite updatedDemande = demandeVisiteRepository.save(demandeVisite);

	return demandeVisiteMapper.toDto(updatedDemande);
}
}
