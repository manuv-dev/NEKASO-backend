package gesimmo.nekaso.service;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface CloudinaryService {
    String uploadImage(MultipartFile file);

    List<String> uploadMultipleImages(MultipartFile[] files);

    void deleteImage(String publicId);
}
