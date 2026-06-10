package gesimmo.nekaso.dto.BienImmbilierDTO;

import gesimmo.nekaso.dto.PhotoBienDTO;
import lombok.Builder;

import java.util.List;

@Builder
public record BienImmobilierResponseDTO(
        Long id,
        String typeBien,
        String adresse,
        Double surface,
        Integer nombrePieces,
        Double loyer,
        String statutBien,
        String description,
        String dateAjout,
        List<PhotoBienDTO> photos
) {
}