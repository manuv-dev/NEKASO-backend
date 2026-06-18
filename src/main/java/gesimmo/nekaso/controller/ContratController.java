package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.ContratDTO;

import gesimmo.nekaso.service.ContratService;
import gesimmo.nekaso.shared.Response.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final ContratService contratService;

    @PostMapping("/create-contrat/demande/{id_DemandeLocation}/{conditions}/{dateDebut}")
    public ContratDTO creerContrat(@RequestBody ContratDTO dto,
                                   @PathVariable Long id_DemandeLocation,
                                   @PathVariable String conditions,
                                   @PathVariable String dateDebut) {
        dto.setDemandeLocationId(id_DemandeLocation);
        dto.setConditions(conditions);
        dto.setDateDebut(dateDebut);
        return contratService.creerContrat(dto);
    }

    @GetMapping("/mes-contrats/locataire/{id_Locataire}")
    public ResponseEntity<PageResponse<ContratDTO>> getContratsParLocataire(
            @PathVariable Long id_Locataire,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
        PageResponse<ContratDTO> contrats = PageResponse.fromPage(contratService.getContratsParLocataire(id_Locataire, PageRequest.of(page, size)));

        return new ResponseEntity<>(contrats, HttpStatus.OK);
    }
    @GetMapping("/mes-contrats/gestionnaire/{id_Gestionnaire}")
public ResponseEntity<PageResponse<ContratDTO>> getContratsParGestionnaire(
        @PathVariable Long id_Gestionnaire,
        @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
        @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
    
    PageResponse<ContratDTO> contrats = PageResponse.fromPage(
        contratService.getContratsParGestionnaire(id_Gestionnaire, PageRequest.of(page, size))
    );

    return new ResponseEntity<>(contrats, HttpStatus.OK);
}
}
