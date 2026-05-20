package gesimmo.nekaso.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import gesimmo.nekaso.dto.BienImmobilierDTO;
import gesimmo.nekaso.entity.BienImmobilier;
import gesimmo.nekaso.entity.PhotoBien;
import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
import gesimmo.nekaso.repository.BienImmobilierRepository;
import gesimmo.nekaso.repository.PhotoBienRepository;
import gesimmo.nekaso.service.BienImmobilierService;
import gesimmo.nekaso.service.CloudinaryService;

@Service
public class BienImmobilierServiceImpl implements BienImmobilierService {
    private final BienImmobilierRepository bienImmobilierRepository;
    private final PhotoBienRepository photoBienRepository;
    private final CloudinaryService cloudinaryService;

    public BienImmobilierServiceImpl(
            BienImmobilierRepository bienImmobilierRepository,
            PhotoBienRepository photoBienRepository,
            CloudinaryService cloudinaryService) {
        this.bienImmobilierRepository = bienImmobilierRepository;
        this.photoBienRepository = photoBienRepository;
        this.cloudinaryService = cloudinaryService;
    }

    @Override
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
        return bienImmobilierRepository.findByStatutBienAndTypeBien(
                Statut.valueOf(statut),
                TypeBien.valueOf(type)
        );
    }

    @Override
    public BienImmobilier getBienById(Long id) {
        return bienImmobilierRepository.findById(id).orElse(null);
    }

    @Override
    public BienImmobilier createBien(BienImmobilier bien) {
        return bienImmobilierRepository.save(bien);
    }

    @Override
    public BienImmobilier updateBien(Long id, BienImmobilier bien) {
        BienImmobilier existingBien = bienImmobilierRepository.findById(id).orElse(null);
        if (existingBien != null) {
            existingBien.setTypeBien(bien.getTypeBien());
            existingBien.setAdresse(bien.getAdresse());
            existingBien.setSurface(bien.getSurface());
            existingBien.setNombrePieces(bien.getNombrePieces());
            existingBien.setLoyer(bien.getLoyer());
            existingBien.setStatutBien(bien.getStatutBien());
            existingBien.setDescription(bien.getDescription());
            return bienImmobilierRepository.save(existingBien);
        }
        return null;
    }

    @Transactional
    @Override
    public BienImmobilier updateBien(Long id, BienImmobilierDTO bienDTO, MultipartFile[] photos) {
        BienImmobilier existingBien = bienImmobilierRepository.findById(id).orElse(null);
        if (existingBien != null) {
            existingBien.setTypeBien(bienDTO.getTypeBien());
            existingBien.setAdresse(bienDTO.getAdresse());
            existingBien.setSurface(bienDTO.getSurface());
            existingBien.setNombrePieces(bienDTO.getNombrePieces());
            existingBien.setLoyer(bienDTO.getLoyer());
            existingBien.setStatutBien(bienDTO.getStatutBien());
            existingBien.setDescription(bienDTO.getDescription());
        } else {
            return null;
        }
        return existingBien;
    }


    @Override
    @Transactional
    public BienImmobilier createBien(BienImmobilierDTO bienDTO, MultipartFile[] photos) {

        List<String> photoUrls = cloudinaryService.uploadMultipleImages(photos);

        BienImmobilier bien = new BienImmobilier();
        bien.setTypeBien(bienDTO.getTypeBien());
        bien.setAdresse(bienDTO.getAdresse());
        bien.setSurface(bienDTO.getSurface());
        bien.setNombrePieces(bienDTO.getNombrePieces());
        bien.setLoyer(bienDTO.getLoyer());
        bien.setStatutBien(bienDTO.getStatutBien());
        bien.setDescription(bienDTO.getDescription());
        bien.setDateAjout(LocalDate.now());
        bien.setPhotos(new ArrayList<>());

        BienImmobilier savedBien = bienImmobilierRepository.save(bien);

        // Étape 3 : Créer les PhotoBien avec les URLs Cloudinary
        for (String photoUrl : photoUrls) {
            PhotoBien photo = new PhotoBien();
            photo.setUrlPhoto(photoUrl);
            photo.setDateUpload(LocalDate.now());
            photo.setBien(savedBien);
            photoBienRepository.save(photo);
            savedBien.getPhotos().add(photo);
        }

        // Retourner le bien avec ses photos
        return savedBien;
    }
}
