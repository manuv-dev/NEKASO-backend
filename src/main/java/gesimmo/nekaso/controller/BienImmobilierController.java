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
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;


import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierResponseDTOGes;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierUpdateForm;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.PhotoBien;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.service.BienImmobilierService;
import gesimmo.nekaso.shared.Response.PageResponse;


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

    @GetMapping("/gestionnaire/mes-biens")
    public ResponseEntity<PageResponse<BienImmobilierResponseDTOGes>> getBiensByGestionnaireId(
            Authentication authentication,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
        Gestionnaire gestionnaire = (Gestionnaire) authentication.getPrincipal();
		Long gestionnaireId = gestionnaire.getId();
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

    // @PostMapping(value = "/gestionnaire/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    // public ResponseEntity<BienImmobilierCreateDTO> createBien(@ModelAttribute BienImmobilierForm form) {
    //     MultipartFile[] photosArray = form.getPhotos() != null ? 
    //             form.getPhotos().toArray(new MultipartFile[0]) : new MultipartFile[0];
        
    //     BienImmobilierCreateDTO responseDTO = bienService.createBien(form, photosArray);
        
    //     return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
    // }

    @PostMapping(value = "/gestionnaire/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
public ResponseEntity<BienImmobilierCreateDTO> createBien(
        @ModelAttribute BienImmobilierForm form,
        Authentication authentication) { 
    
    MultipartFile[] photosArray = form.getPhotos() != null ? 
            form.getPhotos().toArray(new MultipartFile[0]) : new MultipartFile[0];
    
    BienImmobilierCreateDTO responseDTO = bienService.createBien(form, photosArray, authentication);
    
    return new ResponseEntity<>(responseDTO, HttpStatus.CREATED);
}

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // Permet à Spring de convertir proprement les chaînes vides reçues sur les fichiers en "null"
        binder.registerCustomEditor(MultipartFile.class, new ByteArrayMultipartFileEditor());
    }
    @PatchMapping(value = "/gestionnaire/update-bien/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<BienImmobilierCreateDTO> updateBien(
            @PathVariable Long id,
            @ModelAttribute BienImmobilierUpdateForm form,
            Authentication authentication) { 
        
        // Extraction et filtrage de sécurité des photos du formulaire
        MultipartFile[] photosArray = new MultipartFile[0];
        if (form.getPhotos() != null) {
            photosArray = form.getPhotos().stream()
                    .filter(p -> p != null && !p.isEmpty() && p.getOriginalFilename() != null && !p.getOriginalFilename().isEmpty())
                    .toArray(MultipartFile[]::new);
        }

        BienImmobilierCreateDTO responseDTO = bienService.updateBien(id, form, photosArray, authentication);
        return ResponseEntity.ok(responseDTO);
    }
}