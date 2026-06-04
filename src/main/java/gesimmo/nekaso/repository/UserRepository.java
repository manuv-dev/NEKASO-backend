package gesimmo.nekaso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByTelephone(String telephone);

    boolean existsByTelephone(String telephone);
}
