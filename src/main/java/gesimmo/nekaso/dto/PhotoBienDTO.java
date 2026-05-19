package gesimmo.nekaso.dto;

import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBienDTO {
    private String urlPhoto;
    private LocalDate dateUpload;
}
