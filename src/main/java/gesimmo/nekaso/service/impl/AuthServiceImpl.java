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
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AuthResponseDTO login(AuthRequestDTO authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getTelephone(), authRequest.getMotDePasse()));

        String token = jwtUtils.generateToken(authentication);
        return new AuthResponseDTO("Bearer", token);
    }

    @Override
    public String register(AuthRequestDTO authRequest) {
        if (userRepository.existsByTelephone(authRequest.getTelephone())) {
            throw new IllegalArgumentException("Telephone already exists.");
        }

        User user = new User();
        user.setNom(authRequest.getNom());
        user.setPrenom(authRequest.getPrenom());
        user.setTelephone(authRequest.getTelephone());
        user.setMotDePasse(passwordEncoder.encode(authRequest.getMotDePasse()));
        user.setRole(Role.LOCATAIRE);
        user.setStatut("ACTIF");
        userRepository.save(user);

        return "User registered successfully.";
    }
}
