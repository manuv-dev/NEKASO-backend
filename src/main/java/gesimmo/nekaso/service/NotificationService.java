package gesimmo.nekaso.service;

import gesimmo.nekaso.entity.Notification;
import gesimmo.nekaso.entity.User;

public interface NotificationService {
    Notification envoyerNotification(User user, String message, String type);
}
