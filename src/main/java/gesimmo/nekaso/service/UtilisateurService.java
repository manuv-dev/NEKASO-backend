package gesimmo.nekaso.service;

import java.util.Optional;

import gesimmo.nekaso.entity.User;

public interface UtilisateurService {
    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    User save(User user);
}
