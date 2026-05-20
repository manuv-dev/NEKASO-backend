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
    private final BienImmobilierService bienImmobilierService;


    public BienImmobilierController(BienImmobilierService bienImmobilierService) {
        this.bienImmobilierService = bienImmobilierService;
    }


    @GetMapping("")
    public ResponseEntity<List<BienImmobilier>> getAllBiens(@RequestParam(defaultValue = "") String statut, @RequestParam(defaultValue = "") String type) {
        return new ResponseEntity<>(bienImmobilierService.searchBienImmobilierByStatut(statut, type), HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<BienImmobilier> createBien(@RequestBody BienImmobilier bien) {
        return new ResponseEntity<>(bienImmobilierService.createBien(bien), HttpStatus.CREATED);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BienImmobilier> updateBien(@PathVariable Long id, @RequestBody BienImmobilier bien) {
        BienImmobilier existingBien = bienImmobilierService.getBienById(id);
        if (existingBien == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bienImmobilierService.updateBien(id, bien), HttpStatus.OK);
    }
}