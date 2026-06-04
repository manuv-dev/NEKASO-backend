package gesimmo.nekaso.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.DemandeVisiteDTO;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.service.DemandeVisiteService;

@RestController
@RequestMapping("/api/visites")
public class DemandeVisiteController {

	private final DemandeVisiteService demandeVisiteService;

	public DemandeVisiteController(DemandeVisiteService demandeVisiteService) {
		this.demandeVisiteService = demandeVisiteService;
	}

	@GetMapping("/gestionnaire")
	public ResponseEntity<List<DemandeVisiteDTO>> getDemandesForGestionnaire() {
		return new ResponseEntity<>(demandeVisiteService.getDemandesForGestionnaire(), HttpStatus.OK);
	}

	@PatchMapping("/{id}/approuver")
	public ResponseEntity<Map<String, String>> approuver(@PathVariable Long id) {
		try {
			demandeVisiteService.approuverVisite(id);
			Map<String, String> body = new HashMap<>();
			body.put("message", "Visite approuvée avec succès");
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			throw ex;
		} catch (IllegalStateException ex) {
			Map<String, String> body = new HashMap<>();
			body.put("message", ex.getMessage());
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
	}

	@PatchMapping("/{id}/refuser")
	public ResponseEntity<Map<String, String>> refuser(@PathVariable Long id) {
		try {
			demandeVisiteService.refuserVisite(id);
			Map<String, String> body = new HashMap<>();
			body.put("message", "Visite refusée");
			return new ResponseEntity<>(body, HttpStatus.OK);
		} catch (ResourceNotFoundException ex) {
			throw ex;
		} catch (IllegalStateException ex) {
			Map<String, String> body = new HashMap<>();
			body.put("message", ex.getMessage());
			return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST);
		}
	}

}
