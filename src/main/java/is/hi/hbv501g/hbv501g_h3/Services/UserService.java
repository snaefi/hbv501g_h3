package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface UserService {
    User save(User user);
    void deleteById(Long id);
	Page<User> findAll(Pageable pageable);
    User findById(Long ID);
    User findByUsername(String username);
    User login(User user);
	Page<User> searchUsers(String searchTerm, Pageable pageable);
	User patchUser(Long id, Map<String, Object> updates);

}
