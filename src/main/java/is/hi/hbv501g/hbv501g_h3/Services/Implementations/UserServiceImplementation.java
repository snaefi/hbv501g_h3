package is.hi.hbv501g.hbv501g_h3.Services.Implementations;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.LinkedList;
import java.util.Queue;
import java.util.ArrayList;

@Service
public class UserServiceImplementation implements UserService {
    private List<User> userRepository = new ArrayList<>();
    private Integer id_counter = 0;

    public UserServiceImplementation() {
        //Let chatGPT create 3 random users
        User user1 = new User("knitMaster77", "securePass123", "knitmaster77@example.com",
                new ArrayList<>(), new LinkedList<>());
        User user2 = new User("patternQueen", "superSecret!456", "patternqueen@example.com",
                new ArrayList<>(), new LinkedList<>());
        User user3 = new User("craftyCat", "purrfectKnits", "craftycat@example.com",
                new ArrayList<>(), new LinkedList<>());

        userRepository.add(user1);
        userRepository.add(user2);
        userRepository.add(user3);

        //JPA gives each Pattern an ID, but here we add them manually
        for(User u: userRepository){
            u.setID((long) id_counter);
            id_counter++;
        }
    }

    @Override
    public List<User> findAll(){
        return userRepository;
    }


    @Override
    public User findByID(Long ID){
        for(User u: userRepository){
            if(u.getID().equals(ID)){
                return u;
            }
        }
        return null; //throw error instead of returning null
    }

}
