package is.hi.hbv501g.hbv501g_h3.Persistence.Repositories;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    User save(User user);
    void delete(User user);

	Page<User> findAll(Pageable pageable);
	Page<User> findByUsernameContainingIgnoreCase(String username, Pageable pageable);
    User findByUsername(String username);
    //User findByID(Long ID);
}
