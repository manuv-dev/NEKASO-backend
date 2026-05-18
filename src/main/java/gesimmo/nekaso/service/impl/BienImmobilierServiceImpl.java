package gesimmo.nekaso.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.service.BienImmobilierService;

@Service
public class BienImmobilierServiceImpl implements BienImmobilierService {
    private final BienImmobilierRepository bienImmobilierRepository;

    public BienImmobilierServiceImpl(BienImmobilierRepository bienImmobilierRepository) {
        this.bienImmobilierRepository = bienImmobilierRepository;
    }

    public List<BienImmobilier> searchBienImmobilierByStatut(String statut, String type) {
        if (type.isEmpty() && statut.isEmpty()) {
            return bienImmobilierRepository.findAll();
        }
        if (type.isEmpty()) {
            return bienImmobilierRepository.findByStatutBien(Statut.valueOf(statut));
        }
        if (statut.isEmpty()) {

            return bienImmobilierRepository.findByTypeBien(TypeBien.valueOf(type));
        }
        return bienImmobilierRepository.findByStatutBienAndTypeBien(Statut.valueOf(statut), TypeBien.valueOf(type));

    };

    public BienImmobilier getBienById(Long id) {
        return bienImmobilierRepository.findById(id).orElse(null);
    };

    public BienImmobilier createBien(BienImmobilier bien) {
        return bienImmobilierRepository.save(bien);
    };

}
