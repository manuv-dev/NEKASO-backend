package gesimmo.nekaso.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import gesimmo.nekaso.entity.BienImmobilier;

public interface BienImmobilierService {
 public Page<BienImmobilier> searchBienImmobilierByStatut(String statut, String type,Pageable pageable);

    // public BienImmobilierResponseDTO getBienById(Long id);

   
}
