package gesimmo.nekaso.service;



import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierCreateDTO;
import gesimmo.nekaso.entity.BienImmobilier;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface BienImmobilierService {
    public Page<BienImmobilier> searchBienImmobilierByStatut(String statut,String type, Pageable pageable);
    public Page<BienImmobilier> Flitrer(String libelle, String adresse, double surface, int nombrePieces, double loyer);
    BienImmobilier createBien(BienImmobilierCreateDTO bienDTO, MultipartFile[] photos);
    public Page<BienImmobilier> getBiensByGestionnaireId(Long gestionnaireId, Pageable pageable);
    public Page<BienImmobilier> getAllBienImmobilierDisponble(Pageable pageable);

}
