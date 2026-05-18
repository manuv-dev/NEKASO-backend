package gesimmo.nekaso.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<List<BienImmobilier>> getAllBiens(@RequestParam(defaultValue = "") String statut,@RequestParam(defaultValue = "") String type) {
        return new  ResponseEntity<>(bienImmobilierService.searchBienImmobilierByStatut(statut,type),HttpStatus.OK);
}
}
