package gesimmo.nekaso.service;

import java.util.Optional;
import gesimmo.nekaso.entity.User;

public interface UtilisateurService {
    Optional<User> findByTelephone(String telephone);

    boolean existsByUserTelephone(String telephone);

    boolean existsByTelephone(String telephone);

    User save(User user);
}
