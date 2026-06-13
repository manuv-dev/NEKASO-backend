package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.QuittanceAffichageDTO;
import gesimmo.nekaso.service.PaiementService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class QuittanceController {

//    private final PaiementService paiementService;
//
//    @GetMapping("/locataires/{locataireId}/quittances")
//    public ResponseEntity<List<QuittanceAffichageDTO>> getQuittancesParLocataire(
//            @PathVariable Long locataireId,
//            @RequestParam(required = false) Long bienId) {
//        List<QuittanceAffichageDTO> quittances = paiementService.getQuittancesParLocataire(locataireId, bienId);
//        return ResponseEntity.ok(quittances);
//    }
//
//    @GetMapping("/biens/{bienId}/quittances")
//    public ResponseEntity<List<QuittanceAffichageDTO>> getQuittancesParBien(
//            @PathVariable Long bienId,
//            @RequestParam(required = false) Long locataireId) {
//        List<QuittanceAffichageDTO> quittances = paiementService.getQuittancesParBien(bienId, locataireId);
//        return ResponseEntity.ok(quittances);
//    }
}
