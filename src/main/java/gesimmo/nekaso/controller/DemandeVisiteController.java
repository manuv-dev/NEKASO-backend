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

import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteDTOList;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.DemandeVisite;
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

	public DemandeVisiteController(DemandeVisiteService demandeVisiteService, DemandeVisiteMapper demandeVisiteMapper, BienImmobilierMapper bienImmobilierMapper) {
		this.demandeVisiteService = demandeVisiteService;
		this.demandeVisiteMapper = demandeVisiteMapper;
		this.bienImmobilierMapper = bienImmobilierMapper;
	}

	@PostMapping("/locataire/{id_Locataire}/bien/{id_Bien}")
	public ResponseEntity<CreationRequestResponse> createDemandeVisite(@PathVariable Long id_Locataire,
			@PathVariable Long id_Bien) {
		DemandeVisiteCreateResponseDTO createdDemande = demandeVisiteService.createDemandeVisite(id_Locataire, id_Bien);
		return new ResponseEntity<>(new CreationRequestResponse(
				createdDemande.id(),
				"Demande de visite créée avec succès",
				createdDemande.statut())
				, HttpStatus.CREATED);
	}

	@GetMapping("mes_demandes/{id_Locataire}")

	public ResponseEntity<PageResponse<DemandeVisiteDTOList>> getAllDemandesVisite(
			@PathVariable(required = true) Long id_Locataire,
			@RequestParam(defaultValue = "") String statut,
			@RequestParam(defaultValue = "${api.pagination.default-page}") int page,
			@RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
		Pageable pageable = PageRequest.of(page, size);

		Page<DemandeVisite> demandes = demandeVisiteService.getAllDemandesVisite(pageable, statut, id_Locataire);
		Page<DemandeVisiteDTOList> demandesDto = demandes.map(demandeVisiteMapper::toDtoList);	

		return new ResponseEntity<>(PageResponse.fromPage(demandesDto), HttpStatus.OK);

	}
    @GetMapping("biens_disponibles")
    public ResponseEntity<PageResponse<BienImmobilierResponseDTOGes>> getAllBiens(
            @RequestParam(defaultValue = "") String statut,
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
            
            Pageable pageable = PageRequest.of(page, size);
            Page<BienImmobilier> bienPage = demandeVisiteService.getBiensDisponibles(pageable);
            Page<BienImmobilierResponseDTOGes> bienDto=bienPage.map(bienImmobilierMapper::toDTO);

       return new ResponseEntity<>(PageResponse.fromPage(bienDto), HttpStatus.OK);
    }
}
