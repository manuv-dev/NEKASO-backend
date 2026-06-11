package gesimmo.nekaso.service.impl;

import java.util.UUID;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.dto.AuthRequestDTO;
import gesimmo.nekaso.dto.AuthResponseDTO;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.Role;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.AuthService;
import gesimmo.nekaso.service.UtilisateurService;

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final UtilisateurService utilisateurService;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public AuthServiceImpl(UserRepository userRepository, UtilisateurService utilisateurService) {
		this.userRepository = userRepository;
		this.utilisateurService = utilisateurService;
	}

	@Override
	public AuthResponseDTO login(AuthRequestDTO authRequest) {
		User user = userRepository.findByTelephone(authRequest.getTelephone())
				.orElseThrow(() -> new IllegalArgumentException("Telephone ou mot de passe invalide."));

		if (!passwordEncoder.matches(authRequest.getMotDePasse(), user.getMotDePasse())) {
			throw new IllegalArgumentException("Telephone ou mot de passe invalide.");
		}

		return new AuthResponseDTO("Bearer", UUID.randomUUID().toString());
	}

	@Override
	public String register(AuthRequestDTO authRequest) {
		if (utilisateurService.existsByTelephone(authRequest.getTelephone())) {
			throw new IllegalArgumentException("Telephone already exists.");
		}

		Role role = authRequest.getRole() == null || authRequest.getRole().isBlank()
				? Role.LOCATAIRE
				: Role.valueOf(authRequest.getRole().toUpperCase());
		utilisateurService.createUser(authRequest.getNom(), authRequest.getPrenom(), authRequest.getTelephone(),
				authRequest.getMotDePasse(), role, "ACTIF");

		return "User registered successfully.";
	}
}