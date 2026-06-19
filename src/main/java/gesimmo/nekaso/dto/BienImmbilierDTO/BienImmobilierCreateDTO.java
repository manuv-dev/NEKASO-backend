package gesimmo.nekaso.dto.BienImmbilierDTO;
import java.util.List;
import lombok.Builder;
@Builder
public record BienImmobilierCreateDTO(
    String typeBien,
    String libelle,
    String adresse,
    String statutBien,
    Double surface,
    Integer nombrePieces,
    Double loyer,
    String description,
    Long gestionnaireId,
    List<String> photos
) {

}
