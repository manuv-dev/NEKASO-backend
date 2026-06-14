package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.dto.PaiementResponseDTO;
import gesimmo.nekaso.dto.QuittanceDTO;
import gesimmo.nekaso.dto.QuittanceResponseDTO;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.entity.Quittance;
import gesimmo.nekaso.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping("/api/paiements")
@RequiredArgsConstructor
public class PaiementController {

    private final PaiementService paiementService;

    @PostMapping
    public ResponseEntity<?> creerPaiement(@RequestBody PaiementDTO dto) {
        try {
            Paiement paiement = paiementService.creerPaiement(dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(paiement));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<List<PaiementResponseDTO>> rechercherPaiements(
            @RequestParam(required = false) Long gestionnaireId,
            @RequestParam(required = false) Long bienId,
            @RequestParam(required = false) Long locataireId,
            @RequestParam(required = false) String dateDebut,
            @RequestParam(required = false) String dateFin,
            @RequestParam(required = false) String statut,
            @RequestParam(required = false) String mois,
            @RequestParam(required = false) String typePaiement) {
        try {
            LocalDate debut = parseDate(dateDebut);
            LocalDate fin = parseDate(dateFin);
            List<Paiement> paiements = paiementService.rechercherPaiements(
                    gestionnaireId,
                    bienId,
                    locataireId,
                    debut,
                    fin,
                    statut,
                    mois,
                    typePaiement);
            return ResponseEntity.ok(paiements.stream().map(this::toResponse).toList());
        } catch (DateTimeParseException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/{paiementId}/quittance")
    public ResponseEntity<?> creerQuittance(@PathVariable Long paiementId, @RequestBody QuittanceDTO dto) {
        try {
            Quittance quittance = paiementService.creerQuittance(paiementId, dto);
            return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(quittance));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{paiementId}/quittance")
    public ResponseEntity<?> getQuittance(@PathVariable Long paiementId) {
        Quittance quittance = paiementService.getQuittanceParPaiement(paiementId);
        return quittance != null ? ResponseEntity.ok(toResponse(quittance)) : ResponseEntity.notFound().build();
    }

    private LocalDate parseDate(String value) {
        return value == null || value.isBlank() ? null : LocalDate.parse(value);
    }

    private PaiementResponseDTO toResponse(Paiement paiement) {
        return PaiementResponseDTO.builder()
                .id(paiement.getId())
                .montant(paiement.getMontant())
                .methodePaiement(paiement.getMethodePaiement() != null ? paiement.getMethodePaiement().name() : null)
                .datePaiement(paiement.getDatePaiement())
                .mois(paiement.getMois() != null ? paiement.getMois().name() : null)
                .reference(paiement.getReference())
                .contratId(paiement.getContrat() != null ? paiement.getContrat().getId() : null)
                .build();
    }

    private QuittanceResponseDTO toResponse(Quittance quittance) {
        return QuittanceResponseDTO.builder()
                .id(quittance.getId())
                .numero(quittance.getNumero())
                .dateEmission(quittance.getDateEmission())
                .cheminPDF(quittance.getCheminPDF())
                .montant(quittance.getMontant())
                .paiementId(quittance.getPaiement() != null ? quittance.getPaiement().getId() : null)
                .build();
    }
}
