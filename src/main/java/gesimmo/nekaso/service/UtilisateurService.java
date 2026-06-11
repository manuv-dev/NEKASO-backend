package gesimmo.nekaso.service;

import java.util.Optional;

import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.Locataire;
import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.enums.Role;

public interface UtilisateurService {
    Optional<User> findByTelephone(String telephone);

    boolean existsByUserTelephone(String telephone);

    boolean existsByTelephone(String telephone);

    User save(User user);

    User createUser(String nom, String prenom, String telephone, String motDePasse, Role role, String statut);

    Locataire createLocataire(String nom, String prenom, String telephone, String motDePasse);

    Gestionnaire createGestionnaire(String nom, String prenom, String telephone, String motDePasse);
}
