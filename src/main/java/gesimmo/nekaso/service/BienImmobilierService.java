package gesimmo.nekaso.service;

import java.util.List;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.dto.BienImmobilierDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


public interface BienImmobilierService {
    public Page<BienImmobilier> searchBienImmobilierByStatut(String statut,String type, Pageable pageable);
    public BienImmobilier getBienById(Long id);
    BienImmobilier createBien(BienImmobilierDTO bienDTO, MultipartFile[] photos);



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
