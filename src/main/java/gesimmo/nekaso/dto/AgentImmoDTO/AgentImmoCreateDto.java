package gesimmo.nekaso.dto.AgentImmoDTO;

import lombok.Builder;

@Builder
public record AgentImmoCreateDto(
    String nom, 
    String prenom,
    Long Idgestionnaire,
    String telephone
    ) {
}