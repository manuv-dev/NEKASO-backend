package gesimmo.nekaso.mapper;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.User;
import org.springframework.stereotype.Component;

@Component
public class ContratMapper {

    public ContratDTO toDto(ContratBail contratBail) {
        return ContratDTO.builder()
                .id(contratBail.getId())
                .dateSignature(contratBail.getDateSignature())
                .montantLoyer(contratBail.getMontantLoyer())
                .montantCaution(contratBail.getMontantCaution())
                .conditions(contratBail.getConditions())
                .dateDebut(contratBail.getDateDebut())
                .cheminPDF(contratBail.getCheminPDF())
                .locataire(contratBail.getDemandeLocation().getLocataire())
                .gestionnaire(contratBail.getDemandeLocation().getBien().getGestionnaire())
                .build();
    }

}
