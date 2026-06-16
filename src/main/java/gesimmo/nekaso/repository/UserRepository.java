package gesimmo.nekaso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.Gestionnaire;
import gesimmo.nekaso.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelephone(String telephone);
    Optional<User> findById(Long id);
    Gestionnaire findGestionnaireById(Long id);

    boolean existsByTelephone(String telephone);
}