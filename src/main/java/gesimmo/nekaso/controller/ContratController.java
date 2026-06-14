package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.service.ContratService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import gesimmo.nekaso.mapper.ContratMapper;

@RestController
@RequestMapping("/api/contrats")
public class ContratController {

    private final ContratService contratService;
    private final ContratMapper contratMapper;
    public ContratController(ContratService contratService, ContratMapper contratMapper) {
        this.contratService = contratService;
        this.contratMapper = contratMapper;
    }

    @PostMapping("/create/{demandeLocationId}/loyer/{montantLoyer}/caution/{montantCaution}/conditions/{conditions}/dateDebut/{dateDebut}")
    public ResponseEntity<ContratDTO> createContrat(
            @PathVariable long demandeLocationId,
            @PathVariable Double montantLoyer,
            @PathVariable Double montantCaution,
            @PathVariable String conditions,
            @PathVariable String dateDebut) {

        java.time.LocalDateTime dateDebutParsed = java.time.LocalDateTime.parse(dateDebut);
        ContratDTO contratDTO = contratService.createContrat(demandeLocationId, montantLoyer, montantCaution, conditions, dateDebutParsed);
        return ResponseEntity.status(HttpStatus.CREATED).body(contratDTO);
    }

    @GetMapping("/download/{contratId}")
    public ResponseEntity<byte[]> downloadContrat(@PathVariable Long contratId) {
        byte[] pdfBytes = contratService.telechargerContrat(contratId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDisposition(ContentDisposition.builder("attachment").filename("contrat_" + contratId + ".pdf").build());
        return new ResponseEntity<>(pdfBytes, headers, HttpStatus.OK);
    }

    @GetMapping("/bien/{bienId}/locataire/{locataireId}")
    public ResponseEntity<ContratDTO> getContratByBienIdAndLocataireId(@PathVariable Long bienId, @PathVariable Long locataireId) {
        ContratDTO contratDTO = contratService.getContratByBienIdAndLocataireId(bienId, locataireId);
        return ResponseEntity.ok(contratDTO);
    }

}
