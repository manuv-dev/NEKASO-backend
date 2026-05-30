package gesimmo.nekaso.service;

import java.util.List;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.dto.BienImmobilierDTO;
import org.springframework.web.multipart.MultipartFile;


public interface BienImmobilierService {
    public List<BienImmobilier> searchBienImmobilierByStatut(String statut,String type);
    public BienImmobilier getBienById(Long id);
    BienImmobilier createBien(BienImmobilierDTO bienDTO, MultipartFile[] photos);

    BienImmobilier createBien(BienImmobilier bien);

    public BienImmobilier updateBien(Long id, BienImmobilier bien);

    BienImmobilier updateBien(Long id, BienImmobilierDTO bienDTO, MultipartFile[] photos);
    public BienImmobilier archiverBien(Long id);
     public void desarchiverBien(Long id);
     public List<BienImmobilier> getAllBiens();
     public List<BienImmobilier> getBiensByGestionnaire(Long gestionnaireId);
     public List<BienImmobilier> getBiensByNombrePieces(Integer nombrePiecesMin, Integer nombrePiecesMax);
     public List<BienImmobilier> getBiensByLoyer(Double loyerMin, Double loyerMax);
     public List<BienImmobilier> getBiensByMultipleCriteria(String type, Integer nombrePieces,Double loyerMin, Double loyerMax);
     void deletePhoto(Long id, Long photoId);
}
