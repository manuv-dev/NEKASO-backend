package gesimmo.nekaso.mapper;

import gesimmo.nekaso.dto.DemandeLocationDTO;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.BienImmobilier;

public class DemandeLocationMapper {

    public static DemandeLocationDTO toDTO(DemandeLocation demande) {
        return DemandeLocationDTO.builder()
                .id(demande.getId())
                .statut(demande.getStatut())
                .dateDemande(demande.getDateDemande())
                .locataireId(demande.getLocataire().getId())
                .bienId(demande.getBien().getId())
                .build();
    }

    public static DemandeLocation toEntity(DemandeLocationDTO dto, Locataire locataire, BienImmobilier bien) {
        return DemandeLocation.builder()
                .id(dto.getId())
                .statut(dto.getStatut())
                .dateDemande(dto.getDateDemande())
                .locataire(locataire)
                .bien(bien)
                .build();
    }
}
