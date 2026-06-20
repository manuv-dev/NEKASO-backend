package gesimmo.nekaso.dto.AgentImmoDTO;

import lombok.Builder;

@Builder
public record AgentImmocreateResponseDto(
  
    String nom,
    Long id,
    String prenom,
    String telephone
  
) {
 
    
}
