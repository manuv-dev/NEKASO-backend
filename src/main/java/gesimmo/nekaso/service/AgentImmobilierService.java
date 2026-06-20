package gesimmo.nekaso.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmoCreateRequestDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmocreateResponseDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentListDto;
import gesimmo.nekaso.entity.AgentImmobilier;


public interface AgentImmobilierService {
    // DemandeVisite assignerDemandeVisite(Long idDemande, Long idAgent);
     AgentImmocreateResponseDto createAgentImmo(AgentImmoCreateRequestDto agentImmoCreateRequest, long id_Gestionnaire); 
     Page<AgentImmobilier> getAllAgents(Pageable pageable,Long idGestionnaire);

    
}
