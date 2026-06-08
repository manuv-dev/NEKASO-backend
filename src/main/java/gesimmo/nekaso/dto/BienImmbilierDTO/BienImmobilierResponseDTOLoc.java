package gesimmo.nekaso.dto.BienImmbilierDTO;

import java.util.List;

import gesimmo.nekaso.dto.PhotoBienDTO;
import lombok.Builder;

@Builder
public record BienImmobilierResponseDTOLoc(
    Long id,
    String typeBien,
    String adresse,
    Double loyer,
    String statutBien,
    List<PhotoBienDTO> photos,
   
    String dateAjout
   

) {
}