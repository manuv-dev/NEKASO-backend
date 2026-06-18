package gesimmo.nekaso.auth.dto;



import lombok.Builder;

@Builder
public record RegisterRequestDto(

    String telephone,
    String motDePasse,
    String prenom,
    String nom
) {
    // Un constructeur d'utilité pratique pour créer rapidement une réponse de succès
    // public static RegisterResponseDto success(String message  , List<String> roles) {
    //     return new RegisterResponseDto(true, message , roles, LocalDate.now());
    // }

    // // Un constructeur d'utilité pour les cas d'échec (ex: email déjà existant)
    // public static RegisterResponseDto error(String message) {
    //     return new RegisterResponseDto(false, message, null, null, null, LocalDate.now());
    // }
}
