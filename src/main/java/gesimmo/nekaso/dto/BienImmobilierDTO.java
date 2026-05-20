package gesimmo.nekaso.dto;

import gesimmo.nekaso.entity.enums.Statut;
import gesimmo.nekaso.entity.enums.TypeBien;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BienImmobilierDTO {
    private Long id;
    private TypeBien typeBien;
    private String adresse;
    private Double surface;
    private Integer nombrePieces;
    private Double loyer;
    private Statut statutBien;
    private String description;
    private LocalDate dateAjout;
    private List<String> urlPhotos; // URLs des photos uploadées via Cloudinary
}