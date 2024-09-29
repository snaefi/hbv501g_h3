package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;

import java.util.List;

public interface UserService {
    List<User> findAll();
    User findByID(Long ID);
}
