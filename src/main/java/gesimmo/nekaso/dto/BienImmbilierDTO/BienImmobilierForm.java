package gesimmo.nekaso.dto.BienImmbilierDTO;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import lombok.Data;

@Data
public class BienImmobilierForm {
    private String typeBien;        
    private String libelle;    
    private String adresse;      
    private Double surface;      
    private Integer nombrePieces;  
    private Double loyer;        
    private String description;   
    private Authentication authentication;  
    private List<MultipartFile> photos; 
}