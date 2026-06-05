package gesimmo.nekaso.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import gesimmo.nekaso.dto.AuthRequestDTO;
import gesimmo.nekaso.dto.AuthResponseDTO;
import gesimmo.nekaso.dto.PhoneValidationResponseDTO;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.AuthService;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.validation.SenegalPhoneNumberValidator;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final LocataireRepository locataireRepository;
    private final SenegalPhoneNumberValidator phoneValidator;

    /**
     * Authentification
     * POST /api/auth/login
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthRequestDTO authRequest) {
        try {
            AuthResponseDTO response = authService.login(authRequest);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur d'authentification : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body("Identifiants invalides. Veuillez vérifier votre numéro de téléphone et votre mot de passe.");
        }
    }

    /**
     * Enregistrement d'un nouveau locataire
     * POST /api/auth/register
     */
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody AuthRequestDTO authRequest) {
        try {
            // Enregistrer l'utilisateur
            String userMessage = authService.register(authRequest);

            // Récupérer l'utilisateur nouvellement créé
            String normalizedPhone = phoneValidator.normalizePhoneNumber(authRequest.getTelephone());
            User user = userRepository.findByTelephone(normalizedPhone)
                    .orElseThrow(
                            () -> new IllegalArgumentException("Erreur lors de la création du compte utilisateur."));

            // Créer un profil Locataire lié à cet utilisateur
            Locataire locataire = new Locataire();
            locataire.setUser(user);
            locataireRepository.save(locataire);

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(userMessage);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Erreur d'enregistrement : " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Une erreur s'est produite lors de la création du compte : " + e.getMessage());
        }
    }

    /**
     * Vérifier si un numéro de téléphone existe
     * GET /api/auth/check-phone/{phoneNumber}
     */
    @GetMapping("/check-phone/{phoneNumber}")
    public ResponseEntity<Boolean> checkPhoneExists(@PathVariable String phoneNumber) {
        if (!phoneValidator.isValid(phoneNumber)) {
            return ResponseEntity.badRequest().body(false);
        }

        String normalizedPhone = phoneValidator.normalizePhoneNumber(phoneNumber);
        boolean exists = userRepository.existsByTelephone(normalizedPhone);
        return ResponseEntity.ok(exists);
    }

    /**
     * Valider un numéro de téléphone
     * GET /api/auth/validate-phone/{phoneNumber}
     */
    @GetMapping("/validate-phone/{phoneNumber}")
    public ResponseEntity<PhoneValidationResponseDTO> validatePhone(@PathVariable String phoneNumber) {
        boolean isValid = phoneValidator.isValid(phoneNumber);
        String operator = isValid ? phoneValidator.getOperator(phoneNumber) : "INCONNU";
        String message = isValid ? "Numéro de téléphone valide. Opérateur détecté : " + operator
                : "Numéro de téléphone invalide. Opérateurs supportés : Orange (77, 78), YAS (76), Expresso (70), ProMobile (75).";

        PhoneValidationResponseDTO response = PhoneValidationResponseDTO.builder()
                .valid(isValid)
                .operator(operator)
                .message(message)
                .build();

        return ResponseEntity.ok(response);
    }
}
