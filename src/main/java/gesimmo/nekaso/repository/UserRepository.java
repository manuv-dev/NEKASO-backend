package gesimmo.nekaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

}
