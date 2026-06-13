package gesimmo.nekaso.dto.DemandeLocationDTO;

public record DemandeLocationDTOList(
        Long id,
        String statut,
        String dateCreation,
        Long id_Locataire,
        Long id_Bien
) {
}
