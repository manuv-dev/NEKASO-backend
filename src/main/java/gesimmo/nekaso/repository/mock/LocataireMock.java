package gesimmo.nekaso.repository.mock;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.Role;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.repository.UserRepository;

@Component
@Order(2)
public class LocataireMock implements CommandLineRunner {

	private final LocataireRepository locataireRepository;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public LocataireMock(LocataireRepository locataireRepository, UserRepository userRepository) {
		this.locataireRepository = locataireRepository;
		this.userRepository = userRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		if (locataireRepository.count() == 0) {
			User user1 = createUser("Doe", "John", "123456789", "password");
			User user2 = createUser("Smith", "Jane", "987654321", "password");
			User user3 = createUser("Johnson", "Bob", "555444333", "password");

			Locataire locataire1 = new Locataire();
			locataire1.setUser(user1);
			locataireRepository.save(locataire1);

			Locataire locataire2 = new Locataire();
			locataire2.setUser(user2);
			locataireRepository.save(locataire2);

			Locataire locataire3 = new Locataire();
			locataire3.setUser(user3);
			locataireRepository.save(locataire3);
		}
	}

	private User createUser(String nom, String prenom, String telephone, String motDePasse) {
		User user = new User();
		user.setNom(nom);
		user.setPrenom(prenom);
		user.setTelephone(telephone);
		user.setMotDePasse(passwordEncoder.encode(motDePasse));
		user.setRole(Role.LOCATAIRE);
		user.setStatut("ACTIF");
		return userRepository.save(user);
	}
}
