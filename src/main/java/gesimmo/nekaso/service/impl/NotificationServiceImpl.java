package gesimmo.nekaso.service.impl;

import org.springframework.stereotype.Service;

import gesimmo.nekaso.entity.Notification;
import gesimmo.nekaso.repository.NotificationRepository;
import gesimmo.nekaso.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;

	public NotificationServiceImpl(NotificationRepository notificationRepository) {
		this.notificationRepository = notificationRepository;
	}

	@Override
	public void sendNotification(Notification notification) {
		// Simple persistence of notification. Expand (email, push) as needed.
		notificationRepository.save(notification);
	}
}
