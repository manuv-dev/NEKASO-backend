package gesimmo.nekaso.controller;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gesimmo.nekaso.dto.BienImmobilierResponseDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.service.BienImmobilierService;
import gesimmo.nekaso.shared.Response.PageResponse;

@RestController
@RequestMapping("/api/biens/gestionnaire")
public class BienImmobilierController {
    private final BienImmobilierService bienImmobilierService;
    private final BienImmobilierMapper bienImmobilierMapper;

    public BienImmobilierController(BienImmobilierService bienImmobilierService, BienImmobilierMapper bienImmobilierMapper) {
        this.bienImmobilierService = bienImmobilierService;
        this.bienImmobilierMapper = bienImmobilierMapper;
    }

    @GetMapping("")
    public ResponseEntity<PageResponse<BienImmobilierResponseDTO>> getAllBiens(
            @RequestParam(defaultValue = "") String statut,
            @RequestParam(defaultValue = "") String type,
            @RequestParam(defaultValue = "${api.pagination.default-page}") int page,
            @RequestParam(defaultValue = "${api.pagination.default-size}") int size) {
            
            Pageable pageable = PageRequest.of(page, size);
            Page<BienImmobilier> bienPage = bienImmobilierService.searchBienImmobilierByStatut(statut, type, pageable);
            Page<BienImmobilierResponseDTO> bienDto=bienPage.map(bienImmobilierMapper::toDTO);
           

       return new ResponseEntity<>(PageResponse.fromPage(bienDto), HttpStatus.OK);
    }

    // @GetMapping("/{id}")
    // public ResponseEntity<BienImmobilierResponseDTO> getBienById(@PathVariable Long id) {
    //     return new ResponseEntity<>(bienImmobilierService.getBienById(id), HttpStatus.OK);
    // }

    
    

  
}
