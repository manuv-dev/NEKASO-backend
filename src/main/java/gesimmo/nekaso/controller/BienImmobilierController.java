package gesimmo.nekaso.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.service.BienImmobilierService;
@RestController
@RequestMapping("/api/biens/gestionnaire")
public class BienImmobilierController {
    private final BienImmobilierService bienService;

    public BienImmobilierController(BienImmobilierService bienService) {
        this.bienService = bienService;
    }

    @GetMapping("/gestionnaire")
    public List<BienImmobilier> getAllBiens(@RequestParam(defaultValue = "") String statut,
                                            @RequestParam(defaultValue = "") String type) {
        return bienService.searchBienImmobilierByStatut(statut, type);
    }

    @PostMapping
    public BienImmobilier createBien(@RequestBody BienImmobilier bien) {
        return bienService.createBien(bien);
    }

    @PutMapping("/{id}")
    public BienImmobilier updateBien(@PathVariable Long id, @RequestBody BienImmobilier bien) {
        return bienService.updateBien(id, bien);
    }

    @DeleteMapping("/{id}/photos/{photoId}")
    public void deletePhoto(@PathVariable Long id, @PathVariable Long photoId) {
        bienService.deletePhoto(id, photoId);
    }

    @PatchMapping("/{id}/archiver")
    public ResponseEntity<BienImmobilier> archiverBien(@PathVariable Long id) {
        BienImmobilier bien = bienService.archiverBien(id);
        if (bien != null) {
            return ResponseEntity.ok(bien);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}