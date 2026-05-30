package gesimmo.nekaso.repository;

import gesimmo.nekaso.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
