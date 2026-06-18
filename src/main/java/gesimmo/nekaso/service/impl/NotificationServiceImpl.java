// // NotificationServiceImpl.java
// package gesimmo.nekaso.service.impl;


// import lombok.RequiredArgsConstructor;
// import org.springframework.stereotype.Service;

// @Service
// @RequiredArgsConstructor
// public class NotificationServiceImpl implements NotificationService {

//     private final NotificationRepository notificationRepo;

//     @Override
//     public Notification envoyerNotification(User user, String message, String type) {
//         Notification notif = Notification.builder()
//                 .user(user)
//                 .message(message)
//                 .type(type)
//                 .build();
//         return notificationRepo.save(notif);
//     }
// }
