package gesimmo.nekaso.controller;

import java.util.List;

import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierCreateDTO;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierForm;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import io.swagger.v3.oas.annotations.Operation;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.PhotoBien;
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
 @GetMapping("/test")
    public String testEndpoint() {
        return  "Endpoint de test pour les biens immobiliers fonctionne correctement !";
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

    @GetMapping("/locataire/biens_disponibles")
    public ResponseEntity<PageResponse<BienImmobilierResponseDTOGes>> getAllBiensDisponibles(
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Page<BienImmobilier> bienPage = bienService.getAllBienImmobilierDisponble(pageable);
        Page<BienImmobilierResponseDTOGes> bienDto=bienPage.map(bienImmobilierMapper::toDTO);

        return new ResponseEntity<>(PageResponse.fromPage(bienDto), HttpStatus.OK);
    }

    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Créer un bien immobilier avec plusieurs photos")
    public ResponseEntity<BienImmobilierCreateDTO> createBien(@ModelAttribute BienImmobilierForm form) {
            BienImmobilierCreateDTO bienDTO = BienImmobilierCreateDTO.builder()
                .typeBien(form.getTypeBien())
                .libelle(form.getLibelle())
                .adresse(form.getAdresse())
                .surface(form.getSurface())
                .nombrePieces(form.getNombrePieces())
                .loyer(form.getLoyer())
                .description(form.getDescription())
                .build();
        MultipartFile[] photosArray = form.getPhotos() != null ? 
                form.getPhotos().toArray(new MultipartFile[0]) : new MultipartFile[0];
        BienImmobilier savedBien = bienService.createBien(bienDTO, photosArray);
        List<String> urlsPhotos = new java.util.ArrayList<>();
        if (savedBien.getPhotos() != null) {
            for (PhotoBien photo : savedBien.getPhotos()) {
                urlsPhotos.add(photo.getUrlPhoto());
            }
        }

        BienImmobilierCreateDTO responseDTO = BienImmobilierCreateDTO.builder()
                .typeBien(savedBien.getTypeBien() != null ? savedBien.getTypeBien().name() : null)
                .libelle(savedBien.getLibelle()) 
                .adresse(savedBien.getAdresse())
                .surface(savedBien.getSurface())
                .nombrePieces(savedBien.getNombrePieces())
                .loyer(savedBien.getLoyer())
                .description(savedBien.getDescription())
                .photos(urlsPhotos)
                .build();
        
        return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    }

}