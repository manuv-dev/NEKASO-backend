package gesimmo.nekaso.controller;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.DemandeVisiteDTO.DemandeVisiteCreateResponseDTO;
import gesimmo.nekaso.service.DemandeVisiteService;

@RestController
@RequestMapping("/api/visites")
public class DemandeVisiteController {

	private final DemandeVisiteService demandeVisiteService;

	public DemandeVisiteController(DemandeVisiteService demandeVisiteService) {
		this.demandeVisiteService = demandeVisiteService;
	}
	

	@PostMapping("/locataire/{id_Locataire}/bien/{id_Bien}")
	public ResponseEntity<DemandeVisiteCreateResponseDTO> createDemandeVisite(@PathVariable Long id_Locataire,
		 @PathVariable Long id_Bien) {
		DemandeVisiteCreateResponseDTO createdDemande = demandeVisiteService.createDemandeVisite(id_Locataire, id_Bien);
		return ResponseEntity.status(HttpStatus.CREATED).body(createdDemande);
	}

}