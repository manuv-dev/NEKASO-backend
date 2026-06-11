package gesimmo.nekaso.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.service.BienImmobilierService;
import gesimmo.nekaso.shared.Response.PageResponse;
@RestController
@RequestMapping("/api/biens/gestionnaire")
public class BienImmobilierController {
    private final BienImmobilierService bienService;
    private final BienImmobilierMapper bienImmobilierMapper;

    public BienImmobilierController(BienImmobilierService bienService, BienImmobilierMapper bienImmobilierMapper) {
        this.bienService = bienService;
        this.bienImmobilierMapper = bienImmobilierMapper;
    }

 @GetMapping("")
    public ResponseEntity<PageResponse<BienImmobilierResponseDTOGes>> getAllBiens(
            @RequestParam(defaultValue = "") String statut,
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
            
            Pageable pageable = PageRequest.of(page, size);
            Page<BienImmobilier> bienPage = bienService.searchBienImmobilierByStatut(statut, type, pageable);
            Page<BienImmobilierResponseDTOGes> bienDto=bienPage.map(bienImmobilierMapper::toDTO);
           

       return new ResponseEntity<>(PageResponse.fromPage(bienDto), HttpStatus.OK);
    }

    // @PostMapping
    // public BienImmobilier createBien(@RequestBody BienImmobilier bien) {
    //     return bienService.createBien(bien);
    // }

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