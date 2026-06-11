package gesimmo.nekaso.dto;



import java.time.LocalDate;

import lombok.Builder;
@Builder
public record PhotoBienDTO(
    Long id,
    String urlPhoto,
    LocalDate dateUpload
) {
}
   
