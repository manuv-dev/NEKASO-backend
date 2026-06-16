package gesimmo.nekaso.controller;
import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.service.PaiementService;
import gesimmo.nekaso.shared.Response.PageResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping("/create/{idContrat}/{mois}/{methodePaiement}")
    public PaiementDTO creerContrat(@RequestBody PaiementDTO dto,
                                   @PathVariable Long idContrat,
                                   @PathVariable String mois,
                                   @PathVariable String methodePaiement) {

        dto.setContratId(idContrat);
        dto.setMois(mois);
        dto.setMethodePaiement(methodePaiement);
        return paiementService.CreatePaiement(dto);
    }

    @GetMapping("/historiques-paiements/contrat/{contratId}")
    public ResponseEntity<PageResponse<PaiementDTO>> getHistoryPaiementByContrat(
        @PathVariable Long contratId,
        @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
        @RequestParam(defaultValue = "${api.pagination.default-size}") int size){

    PageResponse<PaiementDTO> paiements = PageResponse.fromPage(paiementService.getPaiementByContratId(contratId, PageRequest.of(page, size)));
        
        return new ResponseEntity<>(paiements, HttpStatus.OK);
    }
}
