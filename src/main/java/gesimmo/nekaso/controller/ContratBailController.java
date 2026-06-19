package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.ContratDTO.ContratBailRequestDTO;
import gesimmo.nekaso.dto.ContratDTO.ContratBailResponseDTO;
import gesimmo.nekaso.service.ContratService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratBailController {

    private final ContratService contratBailService;

    @PostMapping("/creer")
    @Operation(summary = "Éditer un contrat de bail définitif et générer son PDF à partir d'un pré-contrat validé")
    public ResponseEntity<ContratBailResponseDTO> creerContrat(@RequestBody ContratBailRequestDTO requestDTO) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(contratBailService.creerContrat(requestDTO));
    }

    @GetMapping("/locataire/{locataireId}")
    public ResponseEntity<Page<ContratBailResponseDTO>> getContratsParLocataire(
            @PathVariable Long locataireId,
            @org.springdoc.core.annotations.ParameterObject org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(contratBailService.getContratsPourLocataire(locataireId, pageable));
    }

    @GetMapping("/gestionnaire/{gestionnaireId}")
    public ResponseEntity<Page<ContratBailResponseDTO>> getContratsParGestionnaire(
            @PathVariable Long gestionnaireId,
            @org.springdoc.core.annotations.ParameterObject org.springframework.data.domain.Pageable pageable) {
        return ResponseEntity.ok(contratBailService.getContratsPourGestionnaire(gestionnaireId, pageable));
    }
}