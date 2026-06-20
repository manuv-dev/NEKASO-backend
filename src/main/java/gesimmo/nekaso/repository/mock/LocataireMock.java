// package gesimmo.nekaso.repository.mock;

// import java.util.HashSet;
// import java.util.Set;

// import org.springframework.boot.CommandLineRunner;
// import org.springframework.core.annotation.Order;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
// import org.springframework.stereotype.Component;

// import gesimmo.nekaso.auth.entity.Role;
// import gesimmo.nekaso.auth.entity.RoleType;
// import gesimmo.nekaso.entity.Locataire;

// import gesimmo.nekaso.repository.LocataireRepository;
// import gesimmo.nekaso.repository.RoleRepository;
// import gesimmo.nekaso.repository.UserRepository;

// @Component
// @Order(3)
// public class LocataireMock implements CommandLineRunner {

// 	private final LocataireRepository locataireRepository;
// 	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
// 	private final RoleRepository roleRepository;

// 	public LocataireMock(LocataireRepository locataireRepository, UserRepository userRepository, RoleRepository roleRepository) {
// 		this.locataireRepository = locataireRepository;
// 		this.roleRepository = roleRepository;
// 	}

// 	@Override
// 	public void run(String... args) throws Exception {
// 		if(roleRepository.findByRole(RoleType.LOCATAIRE).isEmpty()) {
// 		var role = new Role();
// 		role.setRole(RoleType.LOCATAIRE);
// 		roleRepository.save(role);
// 		}

// 		if (locataireRepository.count() == 0) {
// 			var locataireRole = roleRepository.findByRole(RoleType.LOCATAIRE).orElseThrow(() -> new RuntimeException("Role not found"));
// 			Locataire locataire1 = new Locataire();
// 			locataire1.setNom("Doe");
// 			locataire1.setPrenom("John");
// 			locataire1.setTelephone("123456789");
// 			locataire1.setMotDePasse(passwordEncoder.encode("password"));
// 			locataire1.setRoles(new HashSet<>(Set.of(locataireRole)));
// 			locataireRepository.save(locataire1);
// 		}
// 	}


// 	}

