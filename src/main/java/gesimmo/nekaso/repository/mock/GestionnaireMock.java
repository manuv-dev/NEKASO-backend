package gesimmo.nekaso.repository.mock;

import java.util.HashSet;
import java.util.Set;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import gesimmo.nekaso.auth.entity.Role;
import gesimmo.nekaso.auth.entity.RoleType;
import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.repository.GestionnaireRepository;
import gesimmo.nekaso.repository.RoleRepository;
import gesimmo.nekaso.repository.UserRepository;

@Component
@Order(1)
public class GestionnaireMock implements CommandLineRunner {

	private final GestionnaireRepository gestionnaireRepository;
	private final UserRepository userRepository;
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final RoleRepository roleRepository;

	public GestionnaireMock(GestionnaireRepository gestionnaireRepository, UserRepository userRepository, RoleRepository roleRepository) {
		this.gestionnaireRepository = gestionnaireRepository;
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		if(roleRepository.findByRole(RoleType.GESTIONNAIRE).isEmpty()) {
		var role = new Role();
		role.setRole(RoleType.GESTIONNAIRE);
		roleRepository.save(role);
		}

		if (gestionnaireRepository.count() == 0) {
			var gestionnaireRole = roleRepository.findByRole(RoleType.GESTIONNAIRE).orElseThrow(() -> new RuntimeException("Role not found"));
			Gestionnaire gestionnaire1 = new Gestionnaire();
			gestionnaire1.setNom("Traore");
			gestionnaire1.setPrenom("John");
			gestionnaire1.setTelephone("778492734");
			gestionnaire1.setMotDePasse(passwordEncoder.encode("Password-12"));
			gestionnaire1.setRoles(new HashSet<>(Set.of(gestionnaireRole)));
			gestionnaireRepository.save(gestionnaire1);
		}
	}


	}

