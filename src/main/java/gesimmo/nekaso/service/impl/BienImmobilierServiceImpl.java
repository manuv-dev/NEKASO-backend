package gesimmo.nekaso.service.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierCreateDTO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import gesimmo.nekaso.dto.BienImmobilierDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.PhotoBien;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.PhotoBienRepository;
import gesimmo.nekaso.service.BienImmobilierService;
import gesimmo.nekaso.service.CloudinaryService;

import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import gesimmo.nekaso.mapper.BienImmobilierMapper;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.repository.GestionnaireRepository;

@Service
public class BienImmobilierServiceImpl implements BienImmobilierService {
    private final BienImmobilierRepository bienImmobilierRepository;
    private final PhotoBienRepository photoBienRepository;
    private final CloudinaryService cloudinaryService;
    private final BienImmobilierMapper bienImmobilierMapper;
    private final UserRepository userRep;
    private final GestionnaireRepository gestionnaireRepository;

    public BienImmobilierServiceImpl(
            BienImmobilierRepository bienImmobilierRepository,
            PhotoBienRepository photoBienRepository,
            CloudinaryService cloudinaryService,
            BienImmobilierMapper bienImmobilierMapper,
            UserRepository userRep,
            GestionnaireRepository gestionnaireRepository) {
        this.bienImmobilierRepository = bienImmobilierRepository;
        this.photoBienRepository = photoBienRepository;
        this.cloudinaryService = cloudinaryService;
        this.bienImmobilierMapper = bienImmobilierMapper;
        this.userRep = userRep;
        this.gestionnaireRepository = gestionnaireRepository;

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
            biens = bienImmobilierRepository.findByStatutBien(StatutBien.valueOf(statut.toUpperCase()), pageable);
        } else if (statut.isEmpty()) {
            biens = bienImmobilierRepository.findByTypeBien(TypeBien.valueOf(type.toUpperCase()), pageable);
        } else {
            biens = bienImmobilierRepository.findByStatutBienAndTypeBien(
                    StatutBien.valueOf(statut.toUpperCase()),
                    TypeBien.valueOf(type.toUpperCase()),
                    pageable);
        }

        return biens;
    }
    @Override
    public BienImmobilier createBien(BienImmobilierCreateDTO bienDTO, MultipartFile[] photos) {
        BienImmobilier bien = bienImmobilierMapper.toEntity(bienDTO);
        if (bienDTO.gestionnaireId() != null) {
            Optional<Gestionnaire> gestionnaireOpt = gestionnaireRepository.findById(bienDTO.gestionnaireId());
            if (gestionnaireOpt.isPresent()) {
                Gestionnaire gestionnaire = gestionnaireOpt.get();
                bien.setGestionnaire(gestionnaire);
            } else {
                throw new RuntimeException("Gestionnaire non trouvé.");
            }
        } else {
            throw new RuntimeException("Impossible de créer un bien sans lui assigner un gestionnaire.");
        }
        bien.setStatutBien(StatutBien.DISPONIBLE);
        bien.setDateAjout(LocalDate.now());
        BienImmobilier savedBien = bienImmobilierRepository.save(bien);
        List<PhotoBien> listeDesPhotos = new ArrayList<>();

        if (photos != null) {
            for (MultipartFile photo : photos) {
                String url = cloudinaryService.uploadImage(photo);
                PhotoBien photoBien = new PhotoBien();
                photoBien.setUrlPhoto(url);
                photoBien.setBienImmobilier(savedBien);
                photoBien.setDateUpload(LocalDate.now());
                PhotoBien savedPhoto = photoBienRepository.save(photoBien);
                listeDesPhotos.add(savedPhoto);
            }
        }
        
        savedBien.setPhotos(listeDesPhotos); 
        
        return savedBien;
    }
    @Override
    public Page<BienImmobilier> Flitrer(String libelle, String adresse, double surface, int nombrePieces, double loyer) {
        Page<BienImmobilier> biens = bienImmobilierRepository.findByLibelleAndAdresseAndSurfaceAndNombrePiecesAndLoyer(libelle, adresse, surface, nombrePieces, loyer, Pageable.unpaged());
        if (biens.isEmpty()) {
            throw new RuntimeException("Aucun bien trouvé avec les critères spécifiés");
        }
        return biens;
    }
    @Override
    public Page<BienImmobilier> getBiensByGestionnaireId(Long gestionnaireId, Pageable pageable) {
        Page<BienImmobilier> pages = bienImmobilierRepository.findByGestionnaireId(gestionnaireId, pageable);
        if (pages.isEmpty()) {
            throw new RuntimeException("Aucun bien trouvé pour ce gestionnaire");
        }
        return pages;

    }
    @Override
    public Page<BienImmobilier> getAllBienImmobilierDisponble(Pageable pageable) {
        Page<BienImmobilier> pages = bienImmobilierRepository.GetAllBienImmobilierDisponble(pageable);
        if (pages.isEmpty()) {
            throw new RuntimeException("Aucun bien disponible trouvé");
        }
        return pages;
    }

}
