package gesimmo.nekaso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhotoBienDTO {
    private Long id;
    private String urlPhoto;
    private LocalDate dateUpload;
   
}