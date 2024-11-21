package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Events.PatternSharedEvent;
import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Notification;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventListener {

    @Autowired
    private UserService userService;

    @Autowired
    private PatternService patternService;

    @Autowired
    private NotificationRepository notificationRepository;

    @EventListener
    public void handlePatternSharedEvent(PatternSharedEvent event) {
        System.out.println("Received notif event:" + event);
        // Fetch the target user
        User targetUser = event.getUser();

        // Fetch the pattern
        KnittingPattern pattern = event.getPattern();

        // Create a new notification
        Notification notification = new Notification();
        notification.setMessage("You have been invited to collaborate on the pattern: " + pattern.getTitle());
        notification.setType(Notification.NotificationType.ACCEPT_DECLINE);
        notification.setUser(targetUser); // Associate with the target user
        notification.setPattern(pattern);

        // Save the notification
        notificationRepository.save(notification);
    }
}

