package gesimmo.nekaso.service;

import java.util.List;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.dto.BienImmobilierDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;


public interface BienImmobilierService {
    public List<BienImmobilier> searchBienImmobilierByStatut(String statut,String type);
    public BienImmobilier getBienById(Long id);
    BienImmobilier createBien(BienImmobilierDTO bienDTO, MultipartFile[] photos);

    BienImmobilier createBien(BienImmobilier bien);

    public BienImmobilier updateBien(Long id, BienImmobilier bien);

    //j eveux une methode qui me permet de mettre a jour un bien immobilier en utilisant son id et les nouvelles informations du bien et ça peut être aussi des photos aussi
    @Transactional
    BienImmobilier updateBien(Long id, BienImmobilierDTO bienDTO, MultipartFile[] photos);
}
