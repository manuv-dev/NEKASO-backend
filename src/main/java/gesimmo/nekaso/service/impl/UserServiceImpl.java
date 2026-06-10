package gesimmo.nekaso.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.dto.AuthRequestDTO;
import gesimmo.nekaso.entity.Role;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.UserService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

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
