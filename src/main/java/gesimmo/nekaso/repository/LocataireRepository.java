package gesimmo.nekaso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.Locataire;

public interface LocataireRepository extends JpaRepository<Locataire, Long> {
    Optional<Locataire> findByUserId(Long userId);

    boolean existsByUserId(Long userId);
}
