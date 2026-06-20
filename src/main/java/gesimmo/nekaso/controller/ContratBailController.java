package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.ContratDTO.ContratBailRequestDTO;
import gesimmo.nekaso.dto.ContratDTO.ContratBailResponseDTO;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.service.ContratBailService;
import gesimmo.nekaso.shared.Response.PageResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratBailController {

    private final ContratBailService contratBailService;

    @PostMapping("/gestionnaire/creer")
    public ResponseEntity<ContratBailResponseDTO> creerContrat(@RequestBody ContratBailRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contratBailService.creerContrat(requestDTO));
    }

    @GetMapping("/locataire/mes-contrats")
    public ResponseEntity<PageResponse<ContratBailResponseDTO>> getContratsParLocataire(
            Authentication authentication,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Locataire locataire = (Locataire) authentication.getPrincipal();
		Long locataireId = locataire.getId();
        
        return new ResponseEntity<>(PageResponse.fromPage(contratBailService.getContratsPourLocataire(locataireId, pageable)), HttpStatus.OK);
    }

    @GetMapping("/gestionnaire/mes-contrats")
    public ResponseEntity<PageResponse<ContratBailResponseDTO>> getContratsParGestionnaire(
            Authentication authentication,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Gestionnaire gestionnaire = (Gestionnaire) authentication.getPrincipal();
		Long gestionnaireId = gestionnaire.getId();
        return ResponseEntity.ok(PageResponse.fromPage(contratBailService.getContratsPourGestionnaire(gestionnaireId, pageable)));
    }
}