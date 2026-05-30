package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.ContratDTO;
import gesimmo.nekaso.entity.ContratBail;
import gesimmo.nekaso.entity.DemandeLocation;

import java.util.List;

public interface DemandeLocationService {
    DemandeLocation creerDemande(DemandeLocation demande);

    ContratBail validerDemande(Long demandeId, ContratDTO dto);

    DemandeLocation rejeterDemande(Long demandeId);

    List<DemandeLocation> getDemandesParGestionnaire(Long gestionnaireId);

    List<DemandeLocation> getDemandesParLocataire(Long locataireId);
}
