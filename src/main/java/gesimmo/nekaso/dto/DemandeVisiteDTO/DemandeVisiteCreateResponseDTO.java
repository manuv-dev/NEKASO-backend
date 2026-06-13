package gesimmo.nekaso.dto.DemandeVisiteDTO;


import lombok.Builder;
@Builder
public record DemandeVisiteCreateResponseDTO(
    Long id,
    Long id_Locataire,
    Long id_Bien,
    String dateCreation,
    String statut
) {
    
}
