package gesimmo.nekaso.dto.BienImmbilierDTO;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class BienImmobilierUpdateForm {
    private String typeBien;
    private String statutBien;
    private String libelle;
    private String adresse;
    private Double surface;
    private Integer nombrePieces;
    private Double loyer;
    private String description;
    private Authentication authentication;  
    @Schema(type = "string", format = "binary", description = "Nouvelles photos à ajouter (Max 5 au total)")
    private List<MultipartFile> photos;
}