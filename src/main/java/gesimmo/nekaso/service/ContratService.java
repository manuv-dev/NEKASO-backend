package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import java.util.List;

public interface ContratService {
    ContratBail creerContrat(ContratDTO dto);
    List<ContratBail> getContratsParLocataire(Long locataireId);
    List<ContratBail> getContratsParBien(Long bienId);
    List<ContratBail> getContratsParGestionnaire(Long gestionnaireId);
}
