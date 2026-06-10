package gesimmo.nekaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.Locataire;

public interface LocataireRepository extends JpaRepository<Locataire, Long> {

}
