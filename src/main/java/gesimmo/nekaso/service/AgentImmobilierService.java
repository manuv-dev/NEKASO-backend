package gesimmo.nekaso.service;


import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmoCreateRequestDto;
import gesimmo.nekaso.dto.AgentImmoDTO.AgentImmocreateResponseDto;


public interface AgentImmobilierService {
    // DemandeVisite assignerDemandeVisite(Long idDemande, Long idAgent);
     AgentImmocreateResponseDto createAgentImmo(AgentImmoCreateRequestDto agentImmoCreateRequest, long id_Gestionnaire); 
    
}
