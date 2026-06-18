package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.*;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface GestionnaireRepository extends JpaRepository<Gestionnaire, Long> {
    Optional<Gestionnaire> findById(Long id);
}