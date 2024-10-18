package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;

import java.util.List;

public interface UserService {
    User save(User user);
    void delete(User user);

    List<User> findAll();
    User findById(Long ID);
    List<User> findByUsername(String username);
}
