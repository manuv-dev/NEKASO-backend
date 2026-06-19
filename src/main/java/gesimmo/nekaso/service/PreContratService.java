package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.PreContratDTO.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PreContratService {
    PreContratResponseDTO createPreContrat(PreContratRequestDTO dto);
    Page<PreContratResponseDTO> getPreContratsByLocataire(Long locataireId, Pageable pageable);
    Page<PreContratResponseDTO> getPreContratsByGestionnaire(Long gestionnaireId, Pageable pageable);
    PreContratResponseDTO validerPreContrat(Long id);
    PreContratResponseDTO invaliderPreContrat(Long id);
    PreContratResponseDTO updatePreContrat(Long id, PreContratUpdateRequestDTO dto);
}