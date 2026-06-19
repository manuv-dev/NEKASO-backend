package gesimmo.nekaso.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gesimmo.nekaso.dto.ContratDTO.ContratBailRequestDTO;
import gesimmo.nekaso.dto.ContratDTO.ContratBailResponseDTO;

public interface ContratBailService {
    ContratBailResponseDTO creerContrat(ContratBailRequestDTO dto);
    Page<ContratBailResponseDTO> getContratsPourLocataire(Long locataireId, Pageable pageable);
    
    Page<ContratBailResponseDTO> getContratsPourGestionnaire(Long gestionnaireId, Pageable pageable);
}