package gesimmo.nekaso.service;

import java.util.List;

import gesimmo.nekaso.dto.BienImmobilierDTO;

public interface BienImmobilierService {
    public List<BienImmobilierDTO> searchBienImmobilierByStatut(String statut, String type);

    public BienImmobilierDTO getBienById(Long id);

   
}
