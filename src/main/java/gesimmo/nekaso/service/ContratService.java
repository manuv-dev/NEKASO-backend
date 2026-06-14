package gesimmo.nekaso.service;

import java.time.LocalDateTime;

import gesimmo.nekaso.dto.ContratDTO;

public interface ContratService {
    ContratDTO createContrat(long demandeLocationId,Double montantLoyer, Double montantCaution, String conditions, LocalDateTime dateDebut);
    ContratDTO getContratByBienIdAndLocataireId(Long bienId, Long locataireId);
    byte[] telechargerContrat(Long contratId);
}
