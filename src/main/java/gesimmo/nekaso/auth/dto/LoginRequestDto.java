package gesimmo.nekaso.auth.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequestDto(
     @NotBlank(message = "Le numéro est obligatoire")
    @Pattern(
        regexp = "^(77|78|70|76)[0-9]{7}$",
        message = "Le numéro doit commencer par 77, 78, 70 ou 76 et contenir 9 chiffres"
    )
     String telephone,

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 8, message = "Le mot de passe doit contenir au moins 8 caractères")
    @Pattern(
        regexp = "^(?=.*[A-Z])(?=.*[^a-zA-Z0-9]).+$",
        message = "Le mot de passe doit contenir au moins une majuscule et un caractère spécial"
    )
     String motDePasse
) {
    
}
