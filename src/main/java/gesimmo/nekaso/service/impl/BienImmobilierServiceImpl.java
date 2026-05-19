package gesimmo.nekaso.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import gesimmo.nekaso.dto.BienImmobilierDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.PhotoBien;
import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.exception.ResourceNotFoundException;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.PhotoBienRepository;
import gesimmo.nekaso.service.BienImmobilierService;

@Service
public class BienImmobilierServiceImpl implements BienImmobilierService {
    private final BienImmobilierRepository bienImmobilierRepository;
   
    private final BienImmobilierMapper bienImmobilierMapper = new BienImmobilierMapper();

    public BienImmobilierServiceImpl(BienImmobilierRepository bienImmobilierRepository,
            PhotoBienRepository photoBienRepository
           ) {
        this.bienImmobilierRepository = bienImmobilierRepository;
       
        
    }

    public List<BienImmobilierDTO> searchBienImmobilierByStatut(String statut, String type) {
        if (statut == null) {
            statut = "";
        }
        if (type == null) {
            type = "";
        }
        statut = statut.trim();
        type = type.trim();

        List<BienImmobilier> biens;

        if (type.isEmpty() && statut.isEmpty()) {
            biens = bienImmobilierRepository.findAll();
        } else if (type.isEmpty()) {
            biens = bienImmobilierRepository.findByStatutBien(Statut.valueOf(statut.toUpperCase()));
        } else if (statut.isEmpty()) {
            biens = bienImmobilierRepository.findByTypeBien(TypeBien.valueOf(type.toUpperCase()));
        } else {
            biens = bienImmobilierRepository.findByStatutBienAndTypeBien(
                    Statut.valueOf(statut.toUpperCase()),
                    TypeBien.valueOf(type.toUpperCase()));
        }

        return bienImmobilierMapper.toDTOList(biens);
    }

    public BienImmobilierDTO getBienById(Long id) {
        BienImmobilier bien = bienImmobilierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Bien immobilier introuvable avec l'id : " + id));
        return bienImmobilierMapper.toDTO(bien);
    }

  

            
    }


