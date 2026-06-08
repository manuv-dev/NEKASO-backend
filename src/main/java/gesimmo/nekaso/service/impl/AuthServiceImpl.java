package gesimmo.nekaso.service.impl;

import java.util.Set;

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
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

        String token = jwtUtils.generateToken(authentication);
        return new AuthResponseDTO("Bearer", token);
    }

    @Override
    public String register(AuthRequestDTO authRequest) {
        if (userRepository.existsByUsername(authRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists.");
        }

        User user = new User();
        user.setUsername(authRequest.getUsername());
        user.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        user.setRoles(Set.of(Role.LOCATAIRE));
        userRepository.save(user);

        return "User registered successfully.";
    }
}
