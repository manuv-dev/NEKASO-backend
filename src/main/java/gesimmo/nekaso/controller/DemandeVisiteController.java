package gesimmo.nekaso.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteDTOList;
import gesimmo.nekaso.dto.DemandeVisiteDTO.ProposerCreneauDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratRequestDTO;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.ClotureVisite;
import gesimmo.nekaso.mapper.DemandeVisiteMapper;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.service.DemandeVisiteService;
import gesimmo.nekaso.shared.Response.CreationRequestResponse;
import gesimmo.nekaso.shared.Response.PageResponse;

@RestController
@RequestMapping("/api/visites")
public class DemandeVisiteController {

	private final DemandeVisiteService demandeVisiteService;
	private final DemandeVisiteMapper demandeVisiteMapper;
	private final BienImmobilierMapper bienImmobilierMapper;

	public DemandeVisiteController(DemandeVisiteService demandeVisiteService, DemandeVisiteMapper demandeVisiteMapper,
			BienImmobilierMapper bienImmobilierMapper) {
		this.demandeVisiteService = demandeVisiteService;
		this.demandeVisiteMapper = demandeVisiteMapper;
		this.bienImmobilierMapper = bienImmobilierMapper;

	}

	@PostMapping("/locataire/bien/{id_Bien}")
	public ResponseEntity<CreationRequestResponse> createDemandeVisite(
			@PathVariable Long id_Bien, Authentication authentication) {
		Locataire locataire = (Locataire) authentication.getPrincipal();
		Long id_Locataire = locataire.getId();
		DemandeVisiteCreateResponseDTO createdDemande = demandeVisiteService.createDemandeVisite(id_Locataire, id_Bien);
		return new ResponseEntity<>(new CreationRequestResponse(
				createdDemande.id(),
				"Demande de visite créée avec succès",
				createdDemande.statut()), HttpStatus.CREATED);
	}

	@GetMapping("/locataire/mes_demandes")

	public ResponseEntity<PageResponse<DemandeVisiteDTOList>> getAllDemandesVisite(

			@RequestParam(defaultValue = "") String statut,
			@RequestParam(defaultValue = "${api.pagination.default-page}") int page,
			@RequestParam(defaultValue = "${api.pagination.default-size}") int size, Authentication authentication) {

		Locataire locataire = (Locataire) authentication.getPrincipal();
		Long id_Locataire = locataire.getId();

		Pageable pageable = PageRequest.of(page, size);

		Page<DemandeVisite> demandes = demandeVisiteService.getAllDemandesVisite(pageable, statut, id_Locataire);
		Page<DemandeVisiteDTOList> demandesDto = demandes.map(demandeVisiteMapper::toDtoList);

		return new ResponseEntity<>(PageResponse.fromPage(demandesDto), HttpStatus.OK);

	}

	@GetMapping("/gestionnaire/demande")
	public ResponseEntity<PageResponse<DemandeVisiteDTOList>> getAllDemandesVisiteByGestionnaire(
			@RequestParam(defaultValue = "") String statut,
			@RequestParam(defaultValue = "${api.pagination.default-page}") int page,
			@RequestParam(defaultValue = "${api.pagination.default-size}") int size, Authentication authentication) {
		Gestionnaire gestionnaire = (Gestionnaire) authentication.getPrincipal();
		Long id_Gestionnaire = gestionnaire.getId();
		Pageable pageable = PageRequest.of(page, size);

		Page<DemandeVisite> demandes = demandeVisiteService.getAllDemandesVisiteByGestionnaire(pageable, statut);
		Page<DemandeVisiteDTOList> demandesDto = demandes.map(demandeVisiteMapper::toDtoList);

		return new ResponseEntity<>(PageResponse.fromPage(demandesDto), HttpStatus.OK);
	}

	@PatchMapping("/gestionnaire/demande/{id_Demande}/statut/{statut}")
	public ResponseEntity<CreationRequestResponse> updateDemandeVisiteStatut(@PathVariable Long id_Demande,
			@PathVariable String statut) {
		DemandeVisiteCreateResponseDTO updatedDemande = demandeVisiteService.updateDemandeVisiteStatut(id_Demande,
				statut);
		return new ResponseEntity<>(new CreationRequestResponse(
				updatedDemande.id(),
				"Statut de la demande de visite mis à jour avec succès",
				updatedDemande.statut()), HttpStatus.OK);

	}

	@PatchMapping("/gestionnaire/demande/{id_Demande}/confirmer/bien/{idBien}/agent/{idAgent}")
	public ResponseEntity<CreationRequestResponse> confirmerDemandeVisite(@PathVariable Long id_Demande,
			@PathVariable Long idBien, @PathVariable Long idAgent, @RequestParam String creneauVisite) {
		DemandeVisiteCreateResponseDTO updatedDemande = demandeVisiteService.confirmerDemandeVisite(id_Demande, idBien,
				idAgent);
		return new ResponseEntity<>(new CreationRequestResponse(
				updatedDemande.id(),
				"Statut de la demande de visite mis à jour avec succès",
				updatedDemande.statut()), HttpStatus.OK);

	}

	@PatchMapping("/locataire/demande/{id_Demande}/cloturer")
	public ResponseEntity<CreationRequestResponse> cloturerDemandeVisite(
			@PathVariable Long id_Demande,
			@RequestParam ClotureVisite choix,
			@RequestBody(required = false) PreContratRequestDTO preContratDto) {

		DemandeVisiteCreateResponseDTO updatedDemande = demandeVisiteService.cloturerVisite(id_Demande, choix);

		return new ResponseEntity<>(new CreationRequestResponse(
				updatedDemande.id(),
				"Visite clôturée avec succès (" + choix + ").",
				updatedDemande.statut().toString()), HttpStatus.OK);
	}

	@PostMapping("/gestionnaire/demande/{id_Demande}/proposer-creneau")
	public ResponseEntity<CreationRequestResponse> proposerUnCreneau(
			@PathVariable Long id_Demande,
			@RequestBody ProposerCreneauDTO proposerCreneau) {

		DemandeVisiteCreateResponseDTO updatedDemande = demandeVisiteService.ProposerUnCreneau(id_Demande, proposerCreneau.creneauVisite(), proposerCreneau.IdAgent());

		return new ResponseEntity<>(new CreationRequestResponse(
				updatedDemande.id(),
				"Créneau proposé avec succès.",
				updatedDemande.statut().toString()), HttpStatus.OK);
	}
	@PostMapping("/locataire/demande/{id_Demande}/accepter-creneau")
	public ResponseEntity<CreationRequestResponse> accepterCreneau(
			@PathVariable Long id_Demande) {

		DemandeVisiteCreateResponseDTO updatedDemande = demandeVisiteService.accepterCreneau(id_Demande);

		return new ResponseEntity<>(new CreationRequestResponse(
				updatedDemande.id(),
				"Créneau accepté avec succès.",
				updatedDemande.statut().toString()), HttpStatus.OK);
	}

	@PostMapping("/gestionnaire/demande/{id_Demande}/proposer-precontrat")
	public ResponseEntity<CreationRequestResponse> proposerUnPreContrat(
			@PathVariable Long id_Demande,
			@RequestBody PreContratRequestDTO preContratDto) {

		preContratDto.setDemandeVisiteId(id_Demande);
		DemandeVisiteCreateResponseDTO updatedDemande = demandeVisiteService.proposerUnPreContrat(preContratDto);

		return new ResponseEntity<>(new CreationRequestResponse(
				updatedDemande.id(),
				"Pré-contrat proposé avec succès.",
				updatedDemande.statut().toString()), HttpStatus.OK);
	}


	
}

