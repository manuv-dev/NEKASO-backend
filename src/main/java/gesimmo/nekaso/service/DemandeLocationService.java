package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationDTO;
import gesimmo.nekaso.entity.DemandeLocation;
import gesimmo.nekaso.entity.enums.StatutDemande;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DemandeLocationService {

    Page<DemandeLocation> getAllDemandesLocation(Pageable pageable,String statut);
    Page<DemandeLocation> getAllDemandesLocation(Pageable pageable,String statut, Long id_Locataire);
    public DemandeLocationDTO createDemandeLocation(Long idLocataire, Long idBien);
    public DemandeLocationDTO changerStatutDemandeLocation(Long demandeId, String nouveauStatut);

}
