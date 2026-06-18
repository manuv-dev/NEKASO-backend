package gesimmo.nekaso.auth.service;


    import java.util.Set;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import gesimmo.nekaso.auth.dto.LoginRequestDto;
import gesimmo.nekaso.auth.dto.LoginResponseDto;
import gesimmo.nekaso.auth.dto.RegisterRequestDto;
import gesimmo.nekaso.auth.dto.RegisterResponseDto;
import gesimmo.nekaso.auth.entity.Role;
import gesimmo.nekaso.auth.entity.RoleType;

import gesimmo.nekaso.auth.jwtToken.JwtTokenProvider;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.exception.EntityNotFoundException;
import gesimmo.nekaso.repository.RoleRepository;
import gesimmo.nekaso.repository.UserRepository;
@Service
public class AuthServices {  
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider ;
    private final RoleRepository roleRepository;
    public AuthServices(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.roleRepository = roleRepository;
    }

    public LoginResponseDto login(LoginRequestDto loginRequest) {
        var user = userRepository.findByTelephone(loginRequest.telephone())
        .orElseThrow(() -> new EntityNotFoundException("Invalid telephone or password"));
       
        
        if (!passwordEncoder.matches(loginRequest.motDePasse(), user.getPassword())) {
            throw new EntityNotFoundException("Invalid telephone or password");
        }

  String token = jwtTokenProvider.generateToken(user);
     var roles = user.getRoles().stream().map(role -> role.getRole().name()).toList();
     return new LoginResponseDto(token, user.getNom(), user.getPrenom(), user.getTelephone(), roles);
    }

    public  RegisterResponseDto createUser(RegisterRequestDto registerRequest) {
        if (userRepository.existsByTelephone(registerRequest.telephone())) {
            return new RegisterResponseDto(false, "Le numéro de téléphone est déjà utilisé.", registerRequest.telephone(), null, null);
        }

        Locataire user = new Locataire();
        Role locataireRole = roleRepository.findByRole(RoleType.LOCATAIRE)
        .orElseThrow(() -> new RuntimeException("Erreur : Le rôle LOCATAIRE n'existe pas en base."));
    
  
        user.setTelephone(registerRequest.telephone());
        user.setMotDePasse(passwordEncoder.encode(registerRequest.motDePasse()));
        user.setRoles(Set.of(locataireRole));
        user.setNom(registerRequest.nom());
        user.setPrenom(registerRequest.prenom());
        userRepository.save(user);

        return new RegisterResponseDto(true, "Inscription réussie.", registerRequest.telephone(), null, null);
    }
}

