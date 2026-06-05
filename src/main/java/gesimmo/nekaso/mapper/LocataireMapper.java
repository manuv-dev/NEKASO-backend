package gesimmo.nekaso.mapper;

import org.springframework.stereotype.Component;

import gesimmo.nekaso.dto.LocataireDTO;
import gesimmo.nekaso.entity.Locataire;

@Component
public class LocataireMapper {

    public LocataireDTO toDTO(Locataire locataire) {
        if (locataire == null) {
            return null;
        }

        return LocataireDTO.builder()
                .id(locataire.getId())
                .userId(locataire.getUser() != null ? locataire.getUser().getId() : null)
                .nom(locataire.getUser() != null ? locataire.getUser().getNom() : null)
                .prenom(locataire.getUser() != null ? locataire.getUser().getPrenom() : null)
                .telephone(locataire.getUser() != null ? locataire.getUser().getTelephone() : null)
                .statut(locataire.getUser() != null ? locataire.getUser().getStatut() : null)
                .build();
    }

    public Locataire toEntity(LocataireDTO dto) {
        if (dto == null) {
            return null;
        }

        return Locataire.builder()
                .id(dto.getId())
                .build();
    }
}
