package gesimmo.nekaso.dto.BienImmbilierDTO;
import lombok.Builder;
@Builder
public record BienImmobilierCreateDTO(
    String typeBien,
    String libelle,
    String adresse,
    Double surface,
    Integer nombrePieces,
    Double loyer,
    String description
) {
}
