package gesimmo.nekaso.dto.AgentImmoDTO;

import lombok.Builder;

@Builder
public record AgentImmocreateResponseDto(
  
    String nom,
    String prenom,
    String telephone
  
) {
 
    
}
