package gesimmo.nekaso.service.impl;

import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratRequestDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratResponseDTO;
import gesimmo.nekaso.entity.AgentImmobilier;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.mapper.DemandeVisiteMapper;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.ClotureVisite;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.VisiteStatut;
import gesimmo.nekaso.exception.BienNonDisponibleException;
import gesimmo.nekaso.exception.EntityExistException;
import gesimmo.nekaso.exception.EntityNotFoundException;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.repository.AgentImmobilierRepository;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.service.AgentImmobilierService;
import gesimmo.nekaso.service.DemandeVisiteService;
import gesimmo.nekaso.service.PreContratService;
import jakarta.persistence.EntityExistsException;

import java.util.List;

@Service
public class DemandeVisiteServiceImpl implements DemandeVisiteService {
	private final DemandeVisiteRepository demandeVisiteRepository;
	private final LocataireRepository locataireRepository;
	private final BienImmobilierRepository bienRepository;
	private final DemandeVisiteMapper demandeVisiteMapper;
	private final AgentImmobilierRepository agentImmobilierRepository;
	private final PreContratService preContratService;

	public DemandeVisiteServiceImpl(
			DemandeVisiteRepository demandeVisiteRepository,
			AgentImmobilierService agentImmobilierService,
			LocataireRepository locataireRepository,
			BienImmobilierRepository bienRepository,
			DemandeVisiteMapper demandeVisiteMapper,
			AgentImmobilierRepository agentImmobilierRepository,
			PreContratService preContratService)

	{
		this.demandeVisiteRepository = demandeVisiteRepository;
		this.locataireRepository = locataireRepository;
		this.bienRepository = bienRepository;
		this.demandeVisiteMapper = demandeVisiteMapper;
	
		this.agentImmobilierRepository = agentImmobilierRepository;
		this.preContratService = preContratService;
	}

	@Override
	@Transactional
	public DemandeVisiteCreateResponseDTO createDemandeVisite(Long id_Locataire, Long id_Bien) {

		Locataire locataire = locataireRepository.findById(id_Locataire)
				.orElseThrow(() -> new EntityNotFoundException(
						"Le locataire avec l'ID " + id_Locataire + " n'a pas été trouvé"));
		BienImmobilier bien = bienRepository.findById(id_Bien)
				.orElseThrow(() -> new EntityNotFoundException(
						"Le bien immobilier avec l'ID " + id_Bien + " n'a pas été trouvé"));

		List<VisiteStatut> statutsBloquants = List.of(
				VisiteStatut.EN_ATTENTE,
				VisiteStatut.ANNULEE,
				VisiteStatut.CONFIRMEE,
				VisiteStatut.REFUSEE // ou PROGRAMMEE selon ton énumération
		);
		boolean existeDeja = demandeVisiteRepository.existsByLocataireIdAndBienImmobilierIdAndStatutIn(
				id_Locataire,
				id_Bien,
				statutsBloquants);

		if (existeDeja) {
			throw new EntityExistException("Vous avez déjà une demande en attente pour ce bien.");
		}
		if (!bien.getStatutBien().equals(StatutBien.DISPONIBLE)) {
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
					VisiteStatut.valueOf(statut.toUpperCase()), id_Locataire, pageable);
		}

		if (!demandesPage.hasContent()) {
			throw new EntityNotFoundException("Aucune demande trouvée pour ce locataire (ou ce statut).");
		}

		return demandesPage;
	}

	@Override
	public DemandeVisite annulerDemandeVisite(Long idDemande) {

		DemandeVisite demandeVisite = demandeVisiteRepository.findById(idDemande)
				.orElseThrow(() -> new EntityNotFoundException(
						"La demande de visite avec l'ID " + idDemande + " n'a pas été trouvée"));

		if (demandeVisite.getStatut() == VisiteStatut.ANNULEE) {
			throw new IllegalStateException("Cette demande est déjà annulée");
		}

		if (demandeVisite.getStatut() != VisiteStatut.CONFIRMEE) {
			throw new IllegalStateException(
					"Seules les demandes confirmées peuvent être annulées");
		}

		demandeVisite.setStatut(VisiteStatut.ANNULEE);

		return demandeVisiteRepository.save(demandeVisite);
	}

// 	public DemandeVisiteCreateResponseDTO cloturerVisite(Long idDemande, ClotureVisite choixCloture) {
    
//     DemandeVisite demande = demandeVisiteRepository.findById(idDemande)
//         .orElseThrow(() -> new ResourceNotFoundException("Demande de visite introuvable"));

    
//     if (demande.getStatut() != VisiteStatut.CONFIRMEE) {
//         throw new IllegalStateException("Impossible de clôturer une visite qui n'est pas CONFIRMEE");
//     }

//     // 3. Mise à jour des statuts
//     demande.setStatut(VisiteStatut.TERMINEE);
//     demande.setClotureVisite(choixCloture);

//     // 4. Traitement des choix métiers
//     if (choixCloture == ClotureVisite.AVEC_CONTRAT) {
//         // Optionnel : Déclencher ici la pré-création d'un contrat de location
//         // ex: contratService.creerPreContrat(demande.getLocataire(), demnde.getBienImmobilier());
// 		PreContratResponseDTO createPreContrat(PreContratRequestDTO dto)
//     } 
// 	//else if (choixCloture == ClotureVisite.SANS_CONTRAT) {
// 	// 	// Optionnel : Déclencher ici une notification ou un suivi particulier
// 	// 	// ex: notificationService.envoyerNotification(demande.getLocataire(), "Visite clôturée sans contrat");
// 	// } else {
// 	// 	throw new IllegalArgumentException("Choix de clôture invalide");
        
//     // }

//     // 5. Sauvegarde et retour au format DTO
//     DemandeVisite demanndeMiseAJour = demandeVisiteRepository.save(demande);
//     return demandeVisiteMapper.toDto(demanndeMiseAJour);
// }
	// // @Override
	// public DemandeVisite annulerDemandeVisites(Long id_bien) {
	// BienImmobilier bien = bienRepository.findById(id_bien)
	// .orElseThrow(() -> new EntityNotFoundException("Le bien immobilier avec l'ID
	// " + id_bien + " n'a pas été trouvé"));
	// DemandeVisite demandeVisite =
	// demandeVisiteRepository.findByBienImmobilierIdAndStatut(bien.getId(),
	// VisiteStatut.CONFIRMEE)
	// .orElseThrow(() -> new EntityNotFoundException("La demande de visite avec
	// l'ID " + id_bien + " n'a pas été trouvée"));
	// demandeVisite.setStatut(VisiteStatut.ANNULEE);
	// return demandeVisiteRepository.save(demandeVisite);
	// }
@Override
public DemandeVisiteCreateResponseDTO cloturerVisite(Long idDemande, ClotureVisite choixCloture) {
    
    DemandeVisite demande = demandeVisiteRepository.findById(idDemande)
        .orElseThrow(() -> new ResourceNotFoundException("Demande de visite introuvable"));
	

    if (demande.getStatut() != VisiteStatut.CONFIRMEE) {
        throw new IllegalStateException("Impossible de clôturer une visite qui n'est pas CONFIRMEE");
    }

    demande.setClotureVisite(choixCloture);
	
	return demandeVisiteMapper.toDto(demandeVisiteRepository.save(demande));

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
				.orElseThrow(() -> new EntityNotFoundException(
						"La demande de visite avec l'ID " + id + " n'a pas été trouvée"));

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
@Override
@Transactional
public DemandeVisiteCreateResponseDTO confirmerDemandeVisite(Long id, Long idBien, Long idAgent) {

    BienImmobilier bien = bienRepository.findById(idBien)
            .orElseThrow(() -> new EntityNotFoundException("Bien introuvable avec l'ID " + idBien));

    DemandeVisite demandeAConfirmer = demandeVisiteRepository.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Demande introuvable avec l'ID " + id));

    AgentImmobilier agent = agentImmobilierRepository.findById(idAgent)
            .orElseThrow(() -> new EntityNotFoundException("Agent introuvable avec l'ID " + idAgent));

    for (DemandeVisite dv : bien.getDemandesVisite()) {
        if (!dv.getId().equals(id)) {
            dv.setStatut(VisiteStatut.ANNULEE);
        }
    }
	if (demandeAConfirmer.getStatut() == VisiteStatut.CONFIRMEE) {
		throw new EntityExistsException("Cette demande est déjà confirmée");
	}
    demandeAConfirmer.setStatut(VisiteStatut.CONFIRMEE);

    demandeAConfirmer.setAgent(agent);

    demandeVisiteRepository.saveAll(bien.getDemandesVisite());

    return demandeVisiteMapper.toDto(demandeAConfirmer);
	
}
@Transactional
@Override
public DemandeVisiteCreateResponseDTO ProposerUnCreneau(Long idDemande, String creneauVisite,Long IdAgent){
	
	DemandeVisite demande = demandeVisiteRepository.findById(idDemande)
			.orElseThrow(() -> new EntityNotFoundException("Demande introuvable avec l'ID " + idDemande));

	if (demande.getStatut() != VisiteStatut.EN_ATTENTE) {
		throw new IllegalStateException("Seules les demandes en attente peuvent proposer un créneau.");
	}
	AgentImmobilier agent = agentImmobilierRepository.findById(IdAgent)
			.orElseThrow(() -> new EntityNotFoundException("Agent introuvable avec l'ID " + IdAgent));

	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
	demande.setCreneauVisite(LocalDateTime.parse(creneauVisite, formatter));
	demande.setStatut(VisiteStatut.PROPOSEE);
	demande.setAgent(agent);
	DemandeVisite updatedDemande = demandeVisiteRepository.save(demande);

	return demandeVisiteMapper.toDto(updatedDemande);


};
@Override
public DemandeVisiteCreateResponseDTO accepterCreneau(Long idDemande) {
	DemandeVisite demande = demandeVisiteRepository.findById(idDemande)
			.orElseThrow(() -> new EntityNotFoundException("Demande introuvable avec l'ID " + idDemande));

	if (demande.getStatut() != VisiteStatut.PROPOSEE) {
		throw new IllegalStateException("Seules les demandes avec un créneau proposé peuvent être acceptées.");
	}

	demande.setStatut(VisiteStatut.CONFIRMEE);

	DemandeVisite updatedDemande = demandeVisiteRepository.save(demande);

	return demandeVisiteMapper.toDto(updatedDemande);
}


@Override
@Transactional
public DemandeVisiteCreateResponseDTO proposerUnPreContrat(PreContratRequestDTO preContratDto) {
    
    // 1. Récupération de la demande de visite
    DemandeVisite demande = demandeVisiteRepository.findById(preContratDto.getDemandeVisiteId())
            .orElseThrow(() -> new EntityNotFoundException("Demande introuvable avec l'ID " + preContratDto.getDemandeVisiteId()));
	demande.setStatut(VisiteStatut.TERMINEE);
    // 2. Vérifications de sécurité
    if (demande.getStatut() != VisiteStatut.TERMINEE) {
        throw new IllegalStateException("Seules les demandes terminées peuvent faire l'objet d'un pré-contrat.");
    }
    if (demande.getClotureVisite() != ClotureVisite.AVEC_CONTRAT) {
        throw new IllegalStateException("Le locataire n'a pas validé cette visite avec une option de contrat.");
    }


    if (preContratDto.getDateDebutPrevu() == null) {
        LocalDate dateVisite = demande.getCreneauVisite().toLocalDate();
        // Si la date de visite est dans le passé par rapport à aujourd'hui (2026), on ajuste à aujourd'hui
        if (dateVisite.isBefore(LocalDate.now())) {
            preContratDto.setDateDebutPrevu(LocalDate.now());
        } else {
            preContratDto.setDateDebutPrevu(dateVisite);
        }
    }

    // Lier explicitement l'ID de la visite
    preContratDto.setDemandeVisiteId(demande.getId());
    preContratDto.setDemandeLocationId(null);

    // 4. Appel de ton service de pré-contrat
    preContratService.createPreContrat(preContratDto);

    // 5. Retourner la demande mise à jour
    return demandeVisiteMapper.toDto(demande);
}
}