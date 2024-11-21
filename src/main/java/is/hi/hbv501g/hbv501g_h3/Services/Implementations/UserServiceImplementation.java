package is.hi.hbv501g.hbv501g_h3.Services.Implementations;

import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Notification;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.NotificationRepository;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv501g_h3.Services.ImageUploader;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImplementation implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PatternRepository patternRepository;

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ImageUploader imageUploader;


    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Page<User> getAllUsers(String username, Pageable pageable) {
        return userRepository.searchUsers(username, pageable);
    }

    @Override
    public User createUser(User user) {
        try {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);

            return userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            throw new ApiExceptions.UserAlreadyExists();
        }
    }

    @Override
    public List<Notification> getUserNotifications(User user) {
        // Fetch and return notifications for the user
        return notificationRepository.findByUser_Username(user.getUsername());
    }

    @Override
    public void acceptNotification(User authenticatedUser, Long notificationId) {
        // Fetch the notification
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ApiExceptions.NotificationNotFoundException(notificationId));

        // Ensure the notification belongs to the authenticated user
        if (!notification.getUser().getId().equals(authenticatedUser.getId())) {
            throw new ApiExceptions.InvalidTokenException();
        }

        // Act on the notification: Add the user as a collaborator to the associated pattern
        KnittingPattern pattern = notification.getPattern();
        pattern.addCollaborator(authenticatedUser);
        patternRepository.save(pattern);

        // Dismiss (delete) the notification
        notificationRepository.delete(notification);
    }

    @Override
    public void declineNotification(User user, Long notificationId) {
        // Fetch the notification
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new ApiExceptions.NotificationNotFoundException(notificationId));

        // Validate that the notification belongs to the user
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new ApiExceptions.InvalidTokenException();
        }

        // Delete the notification
        notificationRepository.delete(notification);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUser(User user) {
        if (user.getPassword() != null) {
            // Hash the new password before saving
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }

        return userRepository.save(user);
    }

    @Override
    public void uploadProfileImage(User user, byte[] imageBytes) throws IOException {
        File tempFile = null;
        try {
            // Create a temporary file
            tempFile = File.createTempFile("profile_image", ".tmp");

            // Write byte array to the temporary file
            try (FileOutputStream fos = new FileOutputStream(tempFile)) {
                fos.write(imageBytes);
            }

            String profileURL = imageUploader.uploadImage(tempFile);

            user.setProfilePicture(profileURL);
            userRepository.save(user);
        } finally {
            // Ensure temporary file is deleted after upload
            if (tempFile != null && tempFile.exists()) {
                if (!tempFile.delete()) {
                    System.err.println("Warning: Failed to delete temporary file " + tempFile.getAbsolutePath());
                }
            }
        }
    }
}
