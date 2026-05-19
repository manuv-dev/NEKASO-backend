package gesimmo.nekaso.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.BienImmobilierDTO;
import gesimmo.nekaso.service.BienImmobilierService;

@RestController
@RequestMapping("/api/biens/gestionnaire")
public class BienImmobilierController {
    private final BienImmobilierService bienImmobilierService;

    public BienImmobilierController(BienImmobilierService bienImmobilierService) {
        this.bienImmobilierService = bienImmobilierService;
    }

    @GetMapping("")
    public ResponseEntity<List<BienImmobilierDTO>> getAllBiens(@RequestParam(defaultValue = "") String statut,
            @RequestParam(defaultValue = "") String type) {
        return new ResponseEntity<>(bienImmobilierService.searchBienImmobilierByStatut(statut, type), HttpStatus.OK);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<BienImmobilierDTO> getBienById(@PathVariable Long id) {
    //     return new ResponseEntity<>(bienImmobilierService.getBienById(id), HttpStatus.OK);
    // }

    // @PostMapping("")
    // public ResponseEntity<BienImmobilierDTO> createBien(@RequestBody BienImmobilierDTO bienDTO) {
    //     return new ResponseEntity<>(bienImmobilierService.createBien(bienDTO), HttpStatus.CREATED);
    // }
}
