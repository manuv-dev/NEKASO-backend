package gesimmo.nekaso.dto;


import lombok.Builder;

@Builder
public record BienImmobilierDemandeVisiteResponseDTO(
    Long id,
    String typeBien,
    String adresse,
    Double surface,
    Integer nombrePieces,
    Double loyer,
    String statutBien,
    String description,
    String dateAjout
  

) {
}