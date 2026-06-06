package gesimmo.nekaso.controller;



import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.BienImmobilierResponseDTO;
import gesimmo.nekaso.dto.DemandeVisiteResponseDTO;
import gesimmo.nekaso.entity.DemandeVisite;
import gesimmo.nekaso.mapper.DemandeVisiteMapper;
import gesimmo.nekaso.repository.DemandeVisiteRepository;
import gesimmo.nekaso.service.DemandeVisiteService;
import gesimmo.nekaso.shared.Response.PageResponse;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/api/visites")
public class DemandeVisiteController {

	private final DemandeVisiteService demandeVisiteService;
	private final DemandeVisiteMapper demandeVisiteMapper;

	public DemandeVisiteController(DemandeVisiteService demandeVisiteService, DemandeVisiteMapper demandeVisiteMapper) {
		this.demandeVisiteService = demandeVisiteService;
		this.demandeVisiteMapper = demandeVisiteMapper;
	}

	@GetMapping("")
	public ResponseEntity<PageResponse<DemandeVisiteResponseDTO>> getAllDemandesVisite(
			@RequestParam(defaultValue = "") String nom,
			@RequestParam(defaultValue = "") String prenom,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "5") int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<DemandeVisite> demandes = demandeVisiteService.searchDemandesVisiteByLocataire(nom, pageable, prenom);
		Page<DemandeVisiteResponseDTO> demandesDto= demandes.map(demandeVisiteMapper::toDTO);

		return new ResponseEntity<>(PageResponse.fromPage(demandesDto), HttpStatus.OK);
		
	}

}


