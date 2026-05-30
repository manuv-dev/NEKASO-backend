package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.service.DemandeLocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demandes")
@RequiredArgsConstructor
public class DemandeLocationController {

    private final DemandeLocationService service;

    @PostMapping
    public DemandeLocation creerDemande(@RequestBody DemandeLocation demande) {
        return service.creerDemande(demande);
    }

    @PutMapping("/{id}/valider")
    public ContratBail validerDemande(@PathVariable Long id, @RequestBody ContratDTO dto) {
        return service.validerDemande(id, dto);
    }

    @PutMapping("/{id}/rejeter")
    public DemandeLocation rejeterDemande(@PathVariable Long id) {
        return service.rejeterDemande(id);
    }

    @GetMapping("/gestionnaire/{gestionnaireId}")
    public List<DemandeLocation> getDemandesParGestionnaire(@PathVariable Long gestionnaireId) {
        return service.getDemandesParGestionnaire(gestionnaireId);
    }

    @GetMapping("/locataire/{locataireId}")
    public List<DemandeLocation> getDemandesParLocataire(@PathVariable Long locataireId) {
        return service.getDemandesParLocataire(locataireId);
    }
}
