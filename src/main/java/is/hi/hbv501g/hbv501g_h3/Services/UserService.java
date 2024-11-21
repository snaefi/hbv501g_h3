package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Notification;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    Optional<User> getUserByUsername(String username);
    Page<User> getAllUsers(String username, Pageable pageable);
    List<Notification> getUserNotifications(User user);
    void acceptNotification(User authenticatedUser, Long notificationId);
    void declineNotification(User user, Long notificationId);
    User createUser(User user);
    void deleteUser(Long id);
    User updateUser(User user);
    void uploadProfileImage(User user, byte[] imageBytes) throws IOException;
}
