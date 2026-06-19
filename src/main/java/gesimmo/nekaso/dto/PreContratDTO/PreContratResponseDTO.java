package gesimmo.nekaso.dto.PreContratDTO;

import lombok.Data;
import java.time.LocalDate;
import gesimmo.nekaso.entity.enums.StatutPreContrat;

@Data
public class PreContratResponseDTO {
    private Long id;
    private LocalDate dateCreation;
    private LocalDate dateDebutPrevu;
    private Integer jourEcheancePaiement;
    private Double montantLoyer;
    private Double montantCaution;
    private String conditions;
    private StatutPreContrat statutPreContrat;
    private Long bienImmobilierId;
    private String bienLibelle;
    private Long locataireId;
    private String locataireNom;
    private String locatairePrenom;
    private String locataireTelephone;
    private Long gestionnaireId;
    private String gestionnaireNom;
    private String gestionnairePrenom;
    private String gestionnaireTelephone;
}