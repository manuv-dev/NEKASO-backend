package gesimmo.nekaso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PhoneValidationResponseDTO {
    private boolean valid;
    private String operator;
    private String message;
}
