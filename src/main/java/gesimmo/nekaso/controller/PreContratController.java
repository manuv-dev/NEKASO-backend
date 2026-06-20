package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.ContratDTO.ContratBailResponseDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratRequestDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratResponseDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratUpdateRequestDTO;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.service.PreContratService;
import gesimmo.nekaso.shared.Response.PageResponse;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/pre-contrats")
@RequiredArgsConstructor
public class PreContratController {

    private final PreContratService preContratService;

    @PostMapping("/gestionnaire/create")
    public ResponseEntity<PreContratResponseDTO> create(@RequestBody PreContratRequestDTO dto) {
        return new ResponseEntity<>(preContratService.createPreContrat(dto), HttpStatus.CREATED);
    }

    @GetMapping("/gestionnaire") 
    public ResponseEntity<PageResponse<PreContratResponseDTO>> getAllPourGestionnaire(
            Authentication authentication, 
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) { 
        Pageable pageable = PageRequest.of(page, size);
        Gestionnaire gestionnaire = (Gestionnaire) authentication.getPrincipal();
		Long gestionnaireId = gestionnaire.getId();
        return ResponseEntity.ok(PageResponse.fromPage(preContratService.getPreContratsByGestionnaire(gestionnaireId, pageable)));
    }
    @GetMapping("/locataire") 
    public ResponseEntity<PageResponse<PreContratResponseDTO>> getAllPourLocataire(
            Authentication authentication, 
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) { 
        Pageable pageable = PageRequest.of(page, size);
        Locataire locataire = (Locataire) authentication.getPrincipal();
		Long locataireId = locataire.getId();
        return ResponseEntity.ok(PageResponse.fromPage(preContratService.getPreContratsByLocataire(locataireId, pageable)));
    }

    @PatchMapping("/locataire/statut/{id}/valider")
    public ResponseEntity<PreContratResponseDTO> valider(@PathVariable Long id) {
        return ResponseEntity.ok(preContratService.validerPreContrat(id));
    }

    @PatchMapping("/locataire/statut/{id}/invalider")
    public ResponseEntity<PreContratResponseDTO> invalider(@PathVariable Long id) {
        return ResponseEntity.ok(preContratService.invaliderPreContrat(id));
    }

    @PatchMapping("/gestionnaire/statut/{id}/modifier")
    public ResponseEntity<PreContratResponseDTO> modifier(
            @PathVariable Long id, 
            @RequestBody PreContratUpdateRequestDTO dto) {
        return ResponseEntity.ok(preContratService.updatePreContrat(id, dto));
    }
}