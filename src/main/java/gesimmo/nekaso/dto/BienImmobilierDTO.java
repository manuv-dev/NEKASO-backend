package gesimmo.nekaso.dto;

import gesimmo.nekaso.entity.enums.StatutBien;
import gesimmo.nekaso.entity.enums.TypeBien;

import lombok.*;

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
    private StatutBien statutBien;
    private String description;
    private LocalDate dateAjout;
    private List<String> urlPhotos;

}