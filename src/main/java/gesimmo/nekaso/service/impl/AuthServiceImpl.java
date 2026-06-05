package gesimmo.nekaso.service.impl;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.config.JwtUtils;
import gesimmo.nekaso.dto.AuthRequestDTO;
import gesimmo.nekaso.dto.AuthResponseDTO;
import gesimmo.nekaso.entity.Role;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.AuthService;
import gesimmo.nekaso.validation.SenegalPhoneNumberValidator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SenegalPhoneNumberValidator phoneValidator;

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        // Valider le numéro de téléphone
        if (!phoneValidator.isValid(authRequest.getTelephone())) {
            throw new IllegalArgumentException("Numéro de téléphone sénégalais invalide.");
        }

        // Normaliser le numéro pour la recherche
        String normalizedPhone = phoneValidator.normalizePhoneNumber(authRequest.getTelephone());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(normalizedPhone, authRequest.getMotDePasse()));

        String token = jwtUtils.generateToken(authentication);
        return new AuthResponseDTO("Bearer", token);
    }

    @Override
    public String register(AuthRequestDTO authRequest) {
        // Valider le numéro de téléphone
        if (!phoneValidator.isValid(authRequest.getTelephone())) {
            throw new IllegalArgumentException("Numéro de téléphone sénégalais invalide. " +
                    "Opérateurs supportés : Orange (77, 78), YAS (76), Expresso (70), ProMobile (75).");
        }

        // Normaliser le numéro
        String normalizedPhone = phoneValidator.normalizePhoneNumber(authRequest.getTelephone());

        // Vérifier si le numéro existe déjà
        if (userRepository.existsByTelephone(normalizedPhone)) {
            throw new IllegalArgumentException("Ce numéro de téléphone est déjà enregistré.");
        }

        // Créer l'utilisateur
        User user = new User();
        user.setNom(authRequest.getNom().trim());
        user.setPrenom(authRequest.getPrenom().trim());
        user.setTelephone(normalizedPhone);
        user.setMotDePasse(passwordEncoder.encode(authRequest.getMotDePasse()));
        user.setRole(Role.LOCATAIRE);
        user.setStatut("ACTIF");
        user.setDateCreation(java.time.LocalDateTime.now());
        userRepository.save(user);

        return "Compte créé avec succès. Opérateur détecté : " + phoneValidator.getOperator(normalizedPhone);
    }
}
