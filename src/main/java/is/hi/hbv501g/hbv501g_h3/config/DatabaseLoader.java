package is.hi.hbv501g.hbv501g_h3.config;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(UserService userService, PatternRepository patternRepository) {
        return args -> {
            User arounr = userService.createUser(new User("arounr", "Testpass1!"));
            User user1 = userService.createUser(new User("user1", "Testpass1!"));

            patternRepository.save(new KnittingPattern("Pattern1", true, "121212,121212,121212", arounr));
            patternRepository.save(new KnittingPattern("testPattern", false, "121212,121212,121212", arounr));
            patternRepository.save(new KnittingPattern("testPattern2", true, "121212,121212,121212", arounr));
            patternRepository.save(new KnittingPattern("AAAA", false, "121212,121212,121212", user1));

            System.out.println("Database initialized with users and patterns.");
        };
    }
}
