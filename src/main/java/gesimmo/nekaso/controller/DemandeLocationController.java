package gesimmo.nekaso.controller;

import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationCreateDTO;
import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTO;
import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTOList;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.mapper.DemandeLocationMapper;
import gesimmo.nekaso.service.DemandeLocationService;

import gesimmo.nekaso.shared.Response.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/locataire/{id_Locataire}/bien/{id_Bien}")
    public ResponseEntity<DemandeLocationDTO> createDemandeLocation(@PathVariable Long id_Locataire,
                                                                          @PathVariable Long id_Bien) {
        DemandeLocationDTO demandeLocationDTO = demandeLocationService.createDemandeLocation(id_Locataire, id_Bien);
        return ResponseEntity.status(HttpStatus.CREATED).body(demandeLocationDTO);

        // return new ResponseEntity<>(new CreationRequestResponse(
		// 		createdDemande.id(),
		// 		"Demande de visite créée avec succès",
		// 		createdDemande.statut())
		// 		, HttpStatus.CREATED);
    }

    @PatchMapping("/demande/{id_Demande}/refuser")
    public ResponseEntity<String> updateDemandeLocationStatus(@PathVariable Long id_Demande) {
        demandeLocationService.refuserDemandeLocation(id_Demande);
        return ResponseEntity.ok("Statut de la demande de location mis à jour avec succès.");

    }

     @PatchMapping("/demande/{id_Demande}/accepter")
     public ResponseEntity<String> accepterDemandeLocation(@PathVariable Long id_Demande) {
            demandeLocationService.accepterDemandeLocation(id_Demande);
            return ResponseEntity.ok("Statut de la demande de location mis à jour avec succès.");
    }
    @PatchMapping("/demande/{id_Demande}/annuler")
    public ResponseEntity<String> annulerDemandeLocation(@PathVariable Long id_Demande) {
        demandeLocationService.annulerDemandeLocation(id_Demande);
        return ResponseEntity.ok("Statut de la demande de location mis à jour avec succès.");
    }

    @GetMapping("/locataire/{id_Locataire}")
    public ResponseEntity<PageResponse<DemandeLocationDTO>> getAllDemandesLocation(
            @PathVariable(required = true) Long id_Locataire,
            @RequestParam(defaultValue = "") String statut,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page <DemandeLocation> demandesPage = demandeLocationService.getAllDemandesLocation(pageable,statut,id_Locataire);
        Page<DemandeLocationDTO> demandeDTO= demandesPage.map(demandeLocationMapper::toDtoList);

        return new ResponseEntity<>(PageResponse.fromPage(demandeDTO), HttpStatus.OK);

    }


}
