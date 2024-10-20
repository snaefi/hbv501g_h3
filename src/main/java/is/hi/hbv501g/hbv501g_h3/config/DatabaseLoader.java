package is.hi.hbv501g.hbv501g_h3.config;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(UserRepository userRepository, PatternRepository patternRepository) {
        return args -> {
            User johnDoe = userRepository.save(new User("JohnDoe", "Testpass1!"));
            User janeDoe = userRepository.save(new User("JaneDoe", "Testpass1!"));
            User johnDoe2 = userRepository.save(new User("JohnDoe2", "Testpass1!"));
            User janeDoe2 = userRepository.save(new User("JaneDoe2", "Testpass1!"));

            patternRepository.save(new KnittingPattern("Pattern1", true, "121212,121212,121212", johnDoe));
            patternRepository.save(new KnittingPattern("testPattern", false, "121212,121212,121212", janeDoe));
            patternRepository.save(new KnittingPattern("testPattern2", true, "121212,121212,121212", johnDoe2));
            patternRepository.save(new KnittingPattern("AAAA", false, "121212,121212,121212", janeDoe2));

            System.out.println("Database initialized with users and patterns.");
        };
    }
}
