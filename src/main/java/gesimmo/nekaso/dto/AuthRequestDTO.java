package gesimmo.nekaso.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {
    private String telephone;
    private String motDePasse;
    private String nom;
    private String prenom;
}
