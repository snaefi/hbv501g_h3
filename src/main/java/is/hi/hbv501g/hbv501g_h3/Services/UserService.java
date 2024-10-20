package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserService {
    Optional<User> getUserById(Long id);
    Page<User> getAllUsers(String username, Pageable pageable);
    User createUser(User user);
    void deleteUser(Long id);
    User updateUser(User user);
}
