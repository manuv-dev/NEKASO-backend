package gesimmo.nekaso.dto;

import java.time.LocalDate;
import java.util.List;

import lombok.Data;

@Data
public class BienImmobilierDTO {
    private Long id;
    private String typeBien;
    private String adresse;
    private Double surface;
    private Integer nombrePieces;
    private Double loyer;
    private String statutBien;
    private String description;
    private LocalDate dateAjout;
    private List<PhotoBienDTO> photos;

}
