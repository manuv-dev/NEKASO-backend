// NotificationService.java
package gesimmo.nekaso.service;

import gesimmo.nekaso.entity.User;
import gesimmo.nekaso.entity.Notification;

public interface NotificationService {
    Notification envoyerNotification(User user, String message, String type);
}
