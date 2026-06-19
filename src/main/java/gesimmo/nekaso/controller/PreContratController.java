package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.PreContratDTO.PreContratRequestDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratResponseDTO;
import gesimmo.nekaso.dto.PreContratDTO.PreContratUpdateRequestDTO;
import gesimmo.nekaso.service.PreContratService;
import lombok.RequiredArgsConstructor;

import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/gestionnaire/{gestionnaireId}") 
    public ResponseEntity<Page<PreContratResponseDTO>> getAllPourGestionnaire(
            @PathVariable Long gestionnaireId, 
            @ParameterObject Pageable pageable) { 
        return ResponseEntity.ok(preContratService.getPreContratsByGestionnaire(gestionnaireId, pageable));
    }

    @GetMapping("/locataire/{locataireId}")
    public ResponseEntity<Page<PreContratResponseDTO>> getAllPourLocataire(
            @PathVariable Long locataireId, @ParameterObject Pageable pageable) {
        return ResponseEntity.ok(preContratService.getPreContratsByLocataire(locataireId, pageable));
    }

    @PatchMapping("/statut/{id}/valider")
    public ResponseEntity<PreContratResponseDTO> valider(@PathVariable Long id) {
        return ResponseEntity.ok(preContratService.validerPreContrat(id));
    }

    @PatchMapping("/statut/{id}/invalider")
    public ResponseEntity<PreContratResponseDTO> invalider(@PathVariable Long id) {
        return ResponseEntity.ok(preContratService.invaliderPreContrat(id));
    }

    @PatchMapping("/statut/{id}/modifier")
    public ResponseEntity<PreContratResponseDTO> modifier(
            @PathVariable Long id, 
            @RequestBody PreContratUpdateRequestDTO dto) {
        return ResponseEntity.ok(preContratService.updatePreContrat(id, dto));
    }
}