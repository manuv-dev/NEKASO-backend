package gesimmo.nekaso.auth.dto;

import java.util.List;

import lombok.Builder;

@Builder
public record LoginResponseDto(
    String token,
    String nom,
    String prenom,
    String telephone,
    List<String> roles
) {
   
}
