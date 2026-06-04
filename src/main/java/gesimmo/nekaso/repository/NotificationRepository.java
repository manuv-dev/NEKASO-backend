package gesimmo.nekaso.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import gesimmo.nekaso.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

}
