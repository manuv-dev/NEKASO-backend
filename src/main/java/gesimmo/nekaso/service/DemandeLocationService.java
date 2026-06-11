package gesimmo.nekaso.service;

import gesimmo.nekaso.dto.DemandeLocationDTO.DemandeLocationCreateDTO;
import gesimmo.nekaso.entity.DemandeLocation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface DemandeLocationService {

    Page<DemandeLocation> getAllDemandesLocation(Pageable pageable,String statut, Long id_Locataire);
    Page<DemandeLocation> getAllDemandesLocationByGestionnaireBienid(Pageable pageable, String statut, Long id_Gestionnaire);
    public DemandeLocationCreateDTO createDemandeLocation(Long idLocataire, Long idBien);
    void refuserDemandeLocation(Long demandeId);
    void  accepterDemandeLocation(Long demandeId);
    public void annulerDemandeLocation(Long demandeId);
}
