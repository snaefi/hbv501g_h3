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
            // Create initial users
            User arounr = userService.createUser(new User("arounr", "Testpass1!"));
            User user1 = userService.createUser(new User("user1", "Testpass1!"));
            User user2 = userService.createUser(new User("user2", "Testpass1!")); // New user

            // Define pattern matrices
            List<String> pattern1Matrix = Arrays.asList("012", "210", "012");
            List<String> pattern2Matrix = Arrays.asList("101", "020", "101");
            List<String> pattern3Matrix = Arrays.asList("221", "101", "221");
            List<String> pattern4Matrix = Arrays.asList("020", "212", "020");
            List<String> pattern5Matrix = Arrays.asList("111", "202", "111");
            List<String> pattern6Matrix = Arrays.asList("110", "011", "110");
            List<String> pattern7Matrix = Arrays.asList("121", "212", "121");
            List<String> pattern8Matrix = Arrays.asList("100", "001", "100");
            List<String> pattern9Matrix = Arrays.asList("010", "101", "010");
            List<String> pattern10Matrix = Arrays.asList("201", "010", "201");

            // Save 10 patterns for arounr
            patternRepository.save(new KnittingPattern("Chevron Stripessssssssssssssssssss", true, pattern1Matrix, arounr));
            patternRepository.save(new KnittingPattern("Cross Stitch", false, pattern2Matrix, arounr));
            patternRepository.save(new KnittingPattern("Diamond Pattern", true, pattern3Matrix, arounr));
            patternRepository.save(new KnittingPattern("Wave Pattern", true, pattern4Matrix, arounr));
            patternRepository.save(new KnittingPattern("Checkerboard", true, pattern5Matrix, arounr));
            patternRepository.save(new KnittingPattern("Hexagon Weave", true, pattern6Matrix, arounr));
            patternRepository.save(new KnittingPattern("Star Pattern", false, pattern7Matrix, arounr));
            patternRepository.save(new KnittingPattern("Linked Squares", true, pattern8Matrix, arounr));
            patternRepository.save(new KnittingPattern("Petals", false, pattern9Matrix, arounr));
            patternRepository.save(new KnittingPattern("Vortex", true, pattern10Matrix, arounr));

            // Save 10 patterns for user1
            patternRepository.save(new KnittingPattern("Mountain Peaks", false, pattern1Matrix, user1));
            patternRepository.save(new KnittingPattern("Zigzag Lines", true, pattern2Matrix, user1));
            patternRepository.save(new KnittingPattern("Abstract Blocks", true, pattern3Matrix, user1));
            patternRepository.save(new KnittingPattern("Honeycomb", true, pattern4Matrix, user1));
            patternRepository.save(new KnittingPattern("Wave Cascade", false, pattern5Matrix, user1));
            patternRepository.save(new KnittingPattern("Forest Hues", true, pattern6Matrix, user1));
            patternRepository.save(new KnittingPattern("Starscape", true, pattern7Matrix, user1));
            patternRepository.save(new KnittingPattern("Crosshatch", false, pattern8Matrix, user1));
            patternRepository.save(new KnittingPattern("Labyrinth", true, pattern9Matrix, user1));
            patternRepository.save(new KnittingPattern("Ripple Effect", false, pattern10Matrix, user1));

            // Save 10 patterns for user3
            patternRepository.save(new KnittingPattern("Starry Night", true, pattern1Matrix, user2));
            patternRepository.save(new KnittingPattern("Forest Trail", false, pattern2Matrix, user2));
            patternRepository.save(new KnittingPattern("Sunburst", true, pattern3Matrix, user2));
            patternRepository.save(new KnittingPattern("Ocean Waves", true, pattern4Matrix, user2));
            patternRepository.save(new KnittingPattern("Brickwork", false, pattern5Matrix, user2));
            patternRepository.save(new KnittingPattern("Galaxy Spiral", true, pattern6Matrix, user2));
            patternRepository.save(new KnittingPattern("Falling Leaves", false, pattern7Matrix, user2));
            patternRepository.save(new KnittingPattern("Hexagon Net", true, pattern8Matrix, user2));
            patternRepository.save(new KnittingPattern("Celtic Knot", true, pattern9Matrix, user2));
            patternRepository.save(new KnittingPattern("Geometric Waves", false, pattern10Matrix, user2));

            System.out.println("Database initialized with users and patterns.");
        };
    }
}
