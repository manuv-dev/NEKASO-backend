package gesimmo.nekaso.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import gesimmo.nekaso.entity.BienImmobilier;

import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.PhotoBienRepository;
import gesimmo.nekaso.service.BienImmobilierService;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BienImmobilierServiceImpl implements BienImmobilierService {
    private final BienImmobilierRepository bienImmobilierRepository;
   
  
  

    public BienImmobilierServiceImpl(BienImmobilierRepository bienImmobilierRepository) {
        this.bienImmobilierRepository = bienImmobilierRepository;
    }




    public Page<BienImmobilier> searchBienImmobilierByStatut(String statut, String type,Pageable pageable) {
        if (statut == null) {
            statut = "";
        }
        if (type == null) {
            type = "";
        }
        statut = statut.trim();
        type = type.trim();

        Page<BienImmobilier> biens;

        if (type.isEmpty() && statut.isEmpty()) {
            biens = bienImmobilierRepository.findAll(pageable);
        } else if (type.isEmpty()) {
            biens = bienImmobilierRepository.findByStatutBien(Statut.valueOf(statut.toUpperCase()), pageable);
        } else if (statut.isEmpty()) {
            biens = bienImmobilierRepository.findByTypeBien(TypeBien.valueOf(type.toUpperCase()), pageable);
        } else {
            biens = bienImmobilierRepository.findByStatutBienAndTypeBien(
                    Statut.valueOf(statut.toUpperCase()),
                    TypeBien.valueOf(type.toUpperCase()),
                    pageable);
        }

        return biens;
    }

    // public BienImmobilierResponseDTO getBienById(Long id) {
    //     BienImmobilier bien = bienImmobilierRepository.findById(id)
    //             .orElseThrow(() -> new ResourceNotFoundException("Bien immobilier introuvable avec l'id : " + id));
    //     return bienImmobilierMapper.toDTO(bien);
    // }

  

            
    }


