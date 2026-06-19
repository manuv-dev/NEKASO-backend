package gesimmo.nekaso.service.impl;

import java.time.LocalDate;
import java.util.Arrays;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierCreateDTO;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierForm;
import gesimmo.nekaso.dto.BienImmbilierDTO.BienImmobilierUpdateForm;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.PhotoBien;
import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.exception.DemandeLocationException;
import gesimmo.nekaso.exception.EntityNotFoundException;
import gesimmo.nekaso.exception.PhotoCountExceededException;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.PhotoBienRepository;
import gesimmo.nekaso.service.BienImmobilierService;
import gesimmo.nekaso.service.CloudinaryService;
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
    @Transactional
    public BienImmobilierCreateDTO createBien(BienImmobilierForm form, MultipartFile[] photos) {

        if (photos != null && photos.length > 5) {
            throw new PhotoCountExceededException("Le nombre de photos ne peut pas dépasser 5 pour un bien immobilier.");
        }

        TypeBien typeBienEnum = TypeBien.valueOf(form.getTypeBien().toUpperCase());

        if (form.getGestionnaireId() == null) {
            throw new IllegalArgumentException("Impossible de créer un bien sans un gestionnaire.");
        }
        boolean existeDeja = bienImmobilierRepository.existsByTypeBienAndLibelleAndAdresseAndSurfaceAndNombrePiecesAndLoyerAndDescriptionAndGestionnaireId(
                typeBienEnum, 
                form.getLibelle(), 
                form.getAdresse(), 
                form.getSurface(), 
                form.getNombrePieces(), 
                form.getLoyer(), 
                form.getDescription(),
                form.getGestionnaireId()
        );

        if (existeDeja) {
            throw new DemandeLocationException("Ce bien immobilier existe déjà pour ce gestionnaire avec des informations identiques.");
        }

        BienImmobilier bien = bienImmobilierMapper.toEntity(form); 
        Gestionnaire gestionnaire = gestionnaireRepository.findById(form.getGestionnaireId())
                .orElseThrow(() -> new EntityNotFoundException("Gestionnaire non trouvé."));
        bien.setGestionnaire(gestionnaire);

        bien.setStatutBien(StatutBien.DISPONIBLE);
        bien.setDateAjout(LocalDate.now());

        BienImmobilier savedBien = bienImmobilierRepository.save(bien);

        if (photos != null && photos.length > 0) {
            for (MultipartFile photo : photos) {
                if (photo != null && !photo.isEmpty() && photo.getOriginalFilename() != null && !photo.getOriginalFilename().isBlank()) {
                    String url = cloudinaryService.uploadImage(photo);
                    PhotoBien photoBien = new PhotoBien();
                    photoBien.setUrlPhoto(url);
                    photoBien.setBienImmobilier(savedBien);
                    photoBien.setDateUpload(LocalDate.now());
                    photoBienRepository.save(photoBien);
                }
            }
        }

        BienImmobilier bienComplet = bienImmobilierRepository.findById(savedBien.getId()).orElse(savedBien);
        return bienImmobilierMapper.toCreateDTO(bienComplet); 
    }

    @Override
    @Transactional
    public BienImmobilierCreateDTO updateBien(Long id, BienImmobilierUpdateForm form, MultipartFile[] photos) {

        BienImmobilier bienExistant = bienImmobilierRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Bien immobilier non trouvé avec l'ID : " + id));

        long nouvellesPhotosValidesCount = 0;
        if (photos != null) {
            nouvellesPhotosValidesCount = Arrays.stream(photos)
                    .filter(p -> p != null && !p.isEmpty() && p.getOriginalFilename() != null && !p.getOriginalFilename().isBlank())
                    .count();
        }

        long totalPhotosApresMaj = bienExistant.getPhotos().size() + nouvellesPhotosValidesCount;
        if (totalPhotosApresMaj > 5) {
            throw new PhotoCountExceededException("Le nombre total de photos pour ce bien ne peut pas dépasser 5. (Actuelles: " 
                    + bienExistant.getPhotos().size() + ", Nouvelles valides: " + nouvellesPhotosValidesCount + ")");
        }

        if (form.getTypeBien() != null && !form.getTypeBien().trim().isBlank()) {
            bienExistant.setTypeBien(TypeBien.valueOf(form.getTypeBien().trim().toUpperCase()));
        }
        
        if (form.getStatutBien() != null && !form.getStatutBien().trim().isBlank()) {
            try {
                bienExistant.setStatutBien(StatutBien.valueOf(form.getStatutBien().trim().toUpperCase()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException("Statut de bien invalide : " + form.getStatutBien());
            }
        }

        if (form.getLibelle() != null && !form.getLibelle().trim().isBlank()) bienExistant.setLibelle(form.getLibelle());
        if (form.getAdresse() != null && !form.getAdresse().trim().isBlank()) bienExistant.setAdresse(form.getAdresse());
        if (form.getDescription() != null && !form.getDescription().trim().isBlank()) bienExistant.setDescription(form.getDescription());
        
        if (form.getSurface() != null) bienExistant.setSurface(form.getSurface());
        if (form.getNombrePieces() != null) bienExistant.setNombrePieces(form.getNombrePieces());
        if (form.getLoyer() != null) bienExistant.setLoyer(form.getLoyer());

        if (form.getGestionnaireId() != null) {
            Gestionnaire gestionnaire = gestionnaireRepository.findById(form.getGestionnaireId())
                    .orElseThrow(() -> new EntityNotFoundException("Gestionnaire non trouvé."));
            bienExistant.setGestionnaire(gestionnaire);
        }

        if (photos != null && photos.length > 0) {
            for (MultipartFile photo : photos) {
                if (photo != null && !photo.isEmpty() && photo.getOriginalFilename() != null && !photo.getOriginalFilename().isBlank()) {
                    String url = cloudinaryService.uploadImage(photo);
                    PhotoBien photoBien = new PhotoBien();
                    photoBien.setUrlPhoto(url);
                    photoBien.setBienImmobilier(bienExistant);
                    photoBien.setDateUpload(LocalDate.now());
                    photoBienRepository.save(photoBien);
                }
            }
        }

        BienImmobilier updatedBien = bienImmobilierRepository.save(bienExistant);
        BienImmobilier bienComplet = bienImmobilierRepository.findById(updatedBien.getId()).orElse(updatedBien);
        return bienImmobilierMapper.toCreateDTO(bienComplet);
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
