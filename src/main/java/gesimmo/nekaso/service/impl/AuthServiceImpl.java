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

@Service
public class AuthServiceImpl implements AuthService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public AuthServiceImpl(UserRepository userRepository) {
		this.userRepository = userRepository;
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
		if (userRepository.existsByTelephone(authRequest.getTelephone())) {
			throw new IllegalArgumentException("Telephone already exists.");
		}

		String nom = authRequest.getNom();
		String prenom = authRequest.getPrenom();
		if (nom == null || nom.isBlank()) {
			nom = authRequest.getTelephone() != null ? authRequest.getTelephone() : "Utilisateur";
		}
		if (prenom == null || prenom.isBlank()) {
			prenom = "Utilisateur";
		}

		User user = new User();
		user.setNom(nom);
		user.setPrenom(prenom);
		user.setTelephone(authRequest.getTelephone());
		user.setMotDePasse(passwordEncoder.encode(authRequest.getMotDePasse()));
		user.setRole(authRequest.getRole() == null || authRequest.getRole().isBlank()
				? Role.LOCATAIRE
				: Role.valueOf(authRequest.getRole().toUpperCase()));
		user.setStatut("ACTIF");
		userRepository.save(user);

		return "User registered successfully.";
	}
}