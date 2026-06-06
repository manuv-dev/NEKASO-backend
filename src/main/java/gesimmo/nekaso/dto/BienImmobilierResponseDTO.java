package gesimmo.nekaso.dto;

import java.util.List;

import lombok.Builder;

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