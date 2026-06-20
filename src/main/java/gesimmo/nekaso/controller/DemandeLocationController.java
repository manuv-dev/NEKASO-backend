package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationCreateDTO;
import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTO;
import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTOList;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.enums.StatutDemande;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.mapper.DemandeLocationMapper;
import gesimmo.nekaso.service.DemandeLocationService;

import gesimmo.nekaso.shared.Response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/demandes")
public class DemandeLocationController {

    private final DemandeLocationService demandeLocationService;
    private final DemandeLocationMapper demandeLocationMapper;

    public DemandeLocationController(DemandeLocationService demandeLocationService, DemandeLocationMapper demandeLocationMapper, BienImmobilierMapper bienImmobilierMapper, DemandeLocationMapper demandeLocationMapper1) {
        this.demandeLocationService = demandeLocationService;
        this.demandeLocationMapper = demandeLocationMapper;
    }

    @PostMapping("/locataire/bien/{id_Bien}")
    public ResponseEntity<DemandeLocationDTO> createDemandeLocation( 
            Authentication authentication,
            @PathVariable Long id_Bien) {
        Locataire locataire = (Locataire) authentication.getPrincipal();
		Long locataireId = locataire.getId();
        DemandeLocationDTO demandeLocationDTO = demandeLocationService.createDemandeLocation(locataireId, id_Bien);
        return ResponseEntity.status(HttpStatus.CREATED).body(demandeLocationDTO);

    }

    @PatchMapping("/gestionnaire/demande/{id_Demande}/statut/{nouveauStatut}")
    public ResponseEntity<DemandeLocationDTO> updateDemandeLocationStatusG(@PathVariable Long id_Demande, @PathVariable String nouveauStatut) {
        DemandeLocationDTO demandeLocationDTO = demandeLocationService.changerStatutDemandeLocation(id_Demande, nouveauStatut);
        return ResponseEntity.ok(demandeLocationDTO);
    }

    @PatchMapping("/locataire/demande/{id_Demande}/statut/{nouveauStatut}")
    public ResponseEntity<DemandeLocationDTO> updateDemandeLocationStatusL(@PathVariable Long id_Demande, @PathVariable String nouveauStatut) {
        DemandeLocationDTO demandeLocationDTO = demandeLocationService.changerStatutDemandeLocation(id_Demande, nouveauStatut);
        return ResponseEntity.ok(demandeLocationDTO);
    }

    @GetMapping("/locataire")
    public ResponseEntity<PageResponse<DemandeLocationDTO>> getAllDemandesLocation(
            Authentication authentication,
            @RequestParam(defaultValue = "") String statut,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {

        Locataire locataire = (Locataire) authentication.getPrincipal();
		Long locataireId = locataire.getId();
        Pageable pageable = PageRequest.of(page, size);
        Page <DemandeLocation> demandesPage = demandeLocationService.getAllDemandesLocation(pageable,statut,locataireId);
        Page<DemandeLocationDTO> demandeDTO= demandesPage.map(demandeLocationMapper::toDtoList);
        return new ResponseEntity<>(PageResponse.fromPage(demandeDTO), HttpStatus.OK);

    }

    @GetMapping("/gestionnaire/demandes-locations")
    public ResponseEntity<PageResponse<DemandeLocationDTO>> getAllDemandesLocation(
            @RequestParam(defaultValue = "") String statut,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page <DemandeLocation> demandesPage = demandeLocationService.getAllDemandesLocation(pageable,statut);
        Page<DemandeLocationDTO> demandeDTO= demandesPage.map(demandeLocationMapper::toDtoList);
        return new ResponseEntity<>(PageResponse.fromPage(demandeDTO), HttpStatus.OK);
    }


}
