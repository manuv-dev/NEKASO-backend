package gesimmo.nekaso.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LocataireDTO {
    private Long id;
    private Long userId;
    private String nom;
    private String prenom;
    private String telephone;
    private String statut;
}
