package gesimmo.nekaso.controller;

import java.util.List;

import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierCreateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.service.BienImmobilierService;

import gesimmo.nekaso.shared.Response.PageResponse;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/biens")
public class BienImmobilierController {
    private final BienImmobilierService bienService;
    private final BienImmobilierMapper bienImmobilierMapper;

    public BienImmobilierController(BienImmobilierService bienService, BienImmobilierMapper bienImmobilierMapper) {
        this.bienService = bienService;
        this.bienImmobilierMapper = bienImmobilierMapper;
    }

 @GetMapping("/gestionnaire")
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

    @GetMapping("/{gestionnaireId}")
    public ResponseEntity<PageResponse<BienImmobilierResponseDTOGes>> getBiensByGestionnaireId(
            @PathVariable Long gestionnaireId,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<BienImmobilier> bienPage = bienService.getBiensByGestionnaireId(gestionnaireId, pageable);
        Page<BienImmobilierResponseDTOGes> bienDto=bienPage.map(bienImmobilierMapper::toDTO);

        return new ResponseEntity<>(PageResponse.fromPage(bienDto), HttpStatus.OK);
    }

    @GetMapping("/locataire")
    public ResponseEntity<PageResponse<BienImmobilierResponseDTOGes>> getAllBiensDisponibles(
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<BienImmobilier> bienPage = bienService.getAllBienImmobilierDisponble(pageable);
        Page<BienImmobilierResponseDTOGes> bienDto=bienPage.map(bienImmobilierMapper::toDTO);

        return new ResponseEntity<>(PageResponse.fromPage(bienDto), HttpStatus.OK);
    }
    @PostMapping(value = "/biens", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BienImmobilierCreateDTO> createBien(
            @RequestPart("bien") BienImmobilierCreateDTO bienDTO,
            @RequestPart(value = "photos", required = true) MultipartFile[] photos) {
        BienImmobilier createdBien = bienService.createBien(bienDTO, photos);
        BienImmobilierCreateDTO responseDTO = bienImmobilierMapper.toCreateDTO(createdBien);
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }
}