package gesimmo.nekaso.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gesimmo.nekaso.dto.LocataireDTO;
import gesimmo.nekaso.service.LocataireService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/locataires")
@RequiredArgsConstructor
public class LocataireController {

    private final LocataireService locataireService;

    /**
     * Créer un nouveau locataire
     * POST /api/locataires
     */
    @PostMapping
    public ResponseEntity<LocataireDTO> createLocataire(@RequestBody LocataireDTO locataireDTO) {
        LocataireDTO createdLocataire = locataireService.createLocataire(locataireDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdLocataire);
    }

    /**
     * Récupérer un locataire par ID
     * GET /api/locataires/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<LocataireDTO> getLocataireById(@PathVariable Long id) {
        LocataireDTO locataire = locataireService.getLocataireById(id);
        return ResponseEntity.ok(locataire);
    }

    /**
     * Récupérer un locataire par ID utilisateur
     * GET /api/locataires/user/{userId}
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<LocataireDTO> getLocataireByUserId(@PathVariable Long userId) {
        LocataireDTO locataire = locataireService.getLocataireByUserId(userId);
        return ResponseEntity.ok(locataire);
    }

    /**
     * Récupérer tous les locataires
     * GET /api/locataires
     */
    @GetMapping
    public ResponseEntity<List<LocataireDTO>> getAllLocataires() {
        List<LocataireDTO> locataires = locataireService.getAllLocataires();
        return ResponseEntity.ok(locataires);
    }

    /**
     * Mettre à jour un locataire
     * PUT /api/locataires/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<LocataireDTO> updateLocataire(
            @PathVariable Long id,
            @RequestBody LocataireDTO locataireDTO) {
        LocataireDTO updatedLocataire = locataireService.updateLocataire(id, locataireDTO);
        return ResponseEntity.ok(updatedLocataire);
    }

    /**
     * Supprimer un locataire
     * DELETE /api/locataires/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteLocataire(@PathVariable Long id) {
        locataireService.deleteLocataire(id);
        return ResponseEntity.ok("Locataire supprimé avec succès.");
    }
}
