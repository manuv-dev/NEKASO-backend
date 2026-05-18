package gesimmo.nekaso.service;

import java.util.List;

import gesimmo.nekaso.entity.BienImmobilier;

public interface BienImmobilierService {
    public List<BienImmobilier> searchBienImmobilierByStatut(String statut,String type);
    public BienImmobilier getBienById(Long id);
    public BienImmobilier createBien(BienImmobilier bien);
}
