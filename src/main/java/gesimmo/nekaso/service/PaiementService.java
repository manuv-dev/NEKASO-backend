package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.PaiementDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PaiementService {
    PaiementDTO CreatePaiement(PaiementDTO dto);
    Page <PaiementDTO> getPaiementByContratId(Long Id,Pageable pageable );
}
