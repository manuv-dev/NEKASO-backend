package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
public interface GestionnaireRepository extends JpaRepository<Gestionnaire, Long> {
}