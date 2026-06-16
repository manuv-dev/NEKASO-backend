package gesimmo.nekaso.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import java.util.List;

public interface ContratService {
    ContratDTO creerContrat(ContratDTO dto);
    Page<ContratDTO> getContratsParLocataire(Long locataireId, Pageable pageable);
    Page<ContratDTO> getContratsParGestionnaire(Long gestionnaireId, Pageable pageable);
    ContratBail getContratById(Long id);
}
