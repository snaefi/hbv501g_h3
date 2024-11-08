package is.hi.hbv501g.hbv501g_h3.config;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DatabaseLoader {

    @Bean
    CommandLineRunner initDatabase(UserService userService, PatternRepository patternRepository) {
        return args -> {
            User arounr = userService.createUser(new User("arounr", "Testpass1!"));
            User user1 = userService.createUser(new User("user1", "Testpass1!"));

            List<String> pattern1Matrix = Arrays.asList("012", "210", "012");
            List<String> pattern2Matrix = Arrays.asList("101", "020", "101");
            List<String> pattern3Matrix = Arrays.asList("221", "101", "221");
            List<String> pattern4Matrix = Arrays.asList("020", "212", "020");
            List<String> pattern5Matrix = Arrays.asList("111", "202", "111");

            patternRepository.save(new KnittingPattern("Chevron Stripes", true, pattern1Matrix, arounr));
            patternRepository.save(new KnittingPattern("Cross Stitch", false, pattern2Matrix, arounr));
            patternRepository.save(new KnittingPattern("Diamond Pattern", true, pattern3Matrix, arounr));
            patternRepository.save(new KnittingPattern("Wave Pattern", true, pattern4Matrix, arounr));
            patternRepository.save(new KnittingPattern("Checkerboard", true, pattern5Matrix, arounr));

            patternRepository.save(new KnittingPattern("Mountain Peaks", false, pattern1Matrix, user1));
            patternRepository.save(new KnittingPattern("Zigzag Lines", true, pattern2Matrix, user1));
            patternRepository.save(new KnittingPattern("Abstract Blocks", true, pattern3Matrix, user1));
            patternRepository.save(new KnittingPattern("Honeycomb", true, pattern4Matrix, user1));
            patternRepository.save(new KnittingPattern("Wave Cascade", false, pattern5Matrix, user1));

            System.out.println("Database initialized with users and patterns.");
        };
    }
}
