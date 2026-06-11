package gesimmo.nekaso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponseDTO {
    private String tokenType;
    private String accessToken;
}
