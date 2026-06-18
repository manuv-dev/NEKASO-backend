package gesimmo.nekaso.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import gesimmo.nekaso.auth.entity.Role;
import gesimmo.nekaso.auth.entity.RoleType;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
     Optional<Role> findByRole(RoleType role);

    
}
