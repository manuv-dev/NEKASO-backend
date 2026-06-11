package gesimmo.nekaso.service.impl;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.Role;
import gesimmo.nekaso.repository.GestionnaireRepository;
import gesimmo.nekaso.repository.LocataireRepository;
import gesimmo.nekaso.repository.UserRepository;
import gesimmo.nekaso.service.UtilisateurService;

@Service
public class UtilisateurServiceImpl implements UtilisateurService {

    private final UserRepository userRepository;
    private final LocataireRepository locataireRepository;
    private final GestionnaireRepository gestionnaireRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public UtilisateurServiceImpl(UserRepository userRepository,
            LocataireRepository locataireRepository,
            GestionnaireRepository gestionnaireRepository) {
        this.userRepository = userRepository;
        this.locataireRepository = locataireRepository;
        this.gestionnaireRepository = gestionnaireRepository;
    }

    @Override
    public Optional<User> findByTelephone(String telephone) {
        return userRepository.findByTelephone(telephone);
    }

    @Override
    public boolean existsByUserTelephone(String telephone) {
        return existsByTelephone(telephone);
    }

    @Override
    public boolean existsByTelephone(String telephone) {
        return userRepository.existsByTelephone(telephone);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User createUser(String nom, String prenom, String telephone, String motDePasse, Role role, String statut) {
        if (telephone != null && existsByTelephone(telephone)) {
            throw new IllegalArgumentException("Telephone already exists.");
        }

        String effectiveNom = nom == null || nom.isBlank() ? (telephone != null ? telephone : "Utilisateur") : nom;
        String effectivePrenom = prenom == null || prenom.isBlank() ? "Utilisateur" : prenom;
        Role effectiveRole = role == null ? Role.LOCATAIRE : role;
        String effectiveStatut = statut == null || statut.isBlank() ? "ACTIF" : statut;

        User user = new User();
        user.setNom(effectiveNom);
        user.setPrenom(effectivePrenom);
        user.setTelephone(telephone);
        user.setMotDePasse(passwordEncoder.encode(motDePasse));
        user.setRole(effectiveRole);
        user.setStatut(effectiveStatut);

        return userRepository.save(user);
    }

    @Override
    public Locataire createLocataire(String nom, String prenom, String telephone, String motDePasse) {
        User user = createUser(nom, prenom, telephone, motDePasse, Role.LOCATAIRE, "ACTIF");
        Locataire locataire = Locataire.builder()
                .user(user)
                .build();
        return locataireRepository.save(locataire);
    }

    @Override
    public Gestionnaire createGestionnaire(String nom, String prenom, String telephone, String motDePasse) {
        String effectiveNom = (nom == null || nom.isBlank()) ? "Fall" : nom.trim();
        String effectivePrenom = (prenom == null || prenom.isBlank()) ? "Amadou" : prenom.trim();
        String effectiveTelephone = (telephone == null || telephone.isBlank())
                ? "+221700000000"
                : telephone.trim();
        String effectivePassword = (motDePasse == null || motDePasse.isBlank()) ? "Password123!" : motDePasse;

        User user = createUser(effectiveNom, effectivePrenom, effectiveTelephone, effectivePassword, Role.GESTIONNAIRE,
                "ACTIF");
        Gestionnaire gestionnaire = new Gestionnaire();
        gestionnaire.setUser(user);
        gestionnaire.setNumeroGestionnaire(gestionnaireRepository.count() + 1);
        return gestionnaireRepository.save(gestionnaire);
    }
}
