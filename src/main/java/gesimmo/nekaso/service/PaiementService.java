package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.PaiementDTO;
import gesimmo.nekaso.dto.QuittanceDTO;
import gesimmo.nekaso.dto.QuittanceAffichageDTO;
import gesimmo.nekaso.entity.Paiement;
import gesimmo.nekaso.entity.Quittance;

import java.time.LocalDate;
import java.util.List;

public interface PaiementService {
    Paiement creerPaiement(PaiementDTO dto);

    List<Paiement> rechercherPaiements(Long gestionnaireId,
            Long bienId,
            Long locataireId,
            LocalDate dateDebut,
            LocalDate dateFin,
            String statut,
            String mois,
            String typePaiement);

    Quittance creerQuittance(Long paiementId, QuittanceDTO dto);

    Quittance getQuittanceParPaiement(Long paiementId);

    // Côté locataire
    List<QuittanceAffichageDTO> getQuittancesParLocataire(Long locataireId, Long bienId);

    List<QuittanceAffichageDTO> getQuittancesParBien(Long bienId, Long locataireId);
}
