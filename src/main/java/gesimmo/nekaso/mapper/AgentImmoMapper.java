package gesimmo.nekaso.mapper;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.AgentImmoDTO.AgentListDto;
import gesimmo.nekaso.entity.AgentImmobilier;

@Component
public class AgentImmoMapper {

    public  AgentListDto toDTO(AgentImmobilier agentImmobilier) {
        return new AgentListDto(
            agentImmobilier.getNom(),
            agentImmobilier.getPrenom(),
            agentImmobilier.getTelephone(),
            agentImmobilier.getId()
        );
    }
    
}
