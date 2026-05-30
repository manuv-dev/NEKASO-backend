package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.service.ContratService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/contrats")
@RequiredArgsConstructor
public class ContratController {

    private final ContratService contratService;

    @PostMapping
    public ContratBail creerContrat(@RequestBody ContratDTO dto) {
        return contratService.creerContrat(dto);
    }
    @GetMapping("/locataire")
    public List<ContratBail> getContratsParLocataire(@RequestParam Long locataireId) {
        return contratService.getContratsParLocataire(locataireId);
    }

    @GetMapping("/bien")
    public List<ContratBail> getContratsParBien(@RequestParam Long bienId) {
        return contratService.getContratsParBien(bienId);
    }

    @GetMapping("/gestionnaire")
    public List<ContratBail> getContratsParGestionnaire(@RequestParam Long gestionnaireId) {
        return contratService.getContratsParGestionnaire(gestionnaireId);
    }
}
