package is.hi.hbv501g.hbv501g_h3.Services.Implementations;

import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Service
public class UserServiceImplementation implements UserService {
    private UserRepository userRepository;

    @Autowired
    public UserServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public List<User> findAll(){
        return userRepository.findAll();
    }


    @Override
    public User findById(Long ID){
        //User user = userRepository.findById(ID).get();
        return userRepository.findById(ID).orElse(null);
    }

    @Override
    public List<User> findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    @Override
    public User login(String username, String password){

    }

}
