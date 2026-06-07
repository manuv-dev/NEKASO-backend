package gesimmo.nekaso.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteDTOList;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.mapper.DemandeVisiteMapper;
import gesimmo.nekaso.service.DemandeVisiteService;
import gesimmo.nekaso.shared.Response.CreationRequestResponse;
import gesimmo.nekaso.shared.Response.PageResponse;

@RestController
@RequestMapping("/api/visites")
public class DemandeVisiteController {

	private final DemandeVisiteService demandeVisiteService;
	private final DemandeVisiteMapper demandeVisiteMapper;

	public DemandeVisiteController(DemandeVisiteService demandeVisiteService, DemandeVisiteMapper demandeVisiteMapper) {
		this.demandeVisiteService = demandeVisiteService;
		this.demandeVisiteMapper = demandeVisiteMapper;	
	}

	@PostMapping("/locataire/{id_Locataire}/bien/{id_Bien}")
	public ResponseEntity<CreationRequestResponse> createDemandeVisite(@PathVariable Long id_Locataire,
			@PathVariable Long id_Bien) {
		DemandeVisiteCreateResponseDTO createdDemande = demandeVisiteService.createDemandeVisite(id_Locataire, id_Bien);
		return new ResponseEntity<>(new CreationRequestResponse(
				createdDemande.id(),
				"Demande de visite créée avec succès",
				createdDemande.dateCreation()), HttpStatus.CREATED);
	}

	@GetMapping("mes_demandes/{id_Locataire}")

	public ResponseEntity<PageResponse<DemandeVisiteDTOList>> getAllDemandesVisite(
			@PathVariable(required = true) Long id_Locataire,
			@RequestParam(defaultValue = "") String statut,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<DemandeVisite> demandes = demandeVisiteService.getAllDemandesVisite(pageable, statut, id_Locataire);
		Page<DemandeVisiteDTOList> demandesDto = demandes.map(demandeVisiteMapper::toDtoList);	

		return new ResponseEntity<>(PageResponse.fromPage(demandesDto), HttpStatus.OK);

	}
}
