package gesimmo.nekaso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gesimmo.nekaso.auth.entity.User;
import gesimmo.nekaso.entity.Gestionnaire;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelephone(String telephone);
    Optional<User> findById(Long id);
    Gestionnaire findGestionnaireById(Long id);

    boolean existsByTelephone(String telephone);
}