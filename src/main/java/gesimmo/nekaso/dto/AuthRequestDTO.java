package gesimmo.nekaso.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import gesimmo.nekaso.validation.ValidSenegalPhoneNumber;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthRequestDTO {

    @NotNull(message = "Le numéro de téléphone est obligatoire")
    @NotBlank(message = "Le numéro de téléphone ne peut pas être vide")
    @ValidSenegalPhoneNumber
    private String telephone;

    @NotNull(message = "Le mot de passe est obligatoire")
    @NotBlank(message = "Le mot de passe ne peut pas être vide")
    @Size(min = 6, message = "Le mot de passe doit avoir au moins 6 caractères")
    private String motDePasse;

    @NotNull(message = "Le nom est obligatoire (pour l'inscription)")
    @NotBlank(message = "Le nom ne peut pas être vide")
    private String nom;

    @NotNull(message = "Le prénom est obligatoire (pour l'inscription)")
    @NotBlank(message = "Le prénom ne peut pas être vide")
    private String prenom;
}
