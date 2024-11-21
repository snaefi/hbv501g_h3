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
            User user1 = userService.createUser(new User("elli", "Testpass1!"));
            User user2 = userService.createUser(new User("snaefi", "Testpass1!"));

            // Define new pattern matrices with updated color palettes
            List<String> pattern1Matrix = Arrays.asList(
                    "10000",
                    "01000",
                    "00100",
                    "00010",
                    "00001"
            );

            List<String> pattern2Matrix = Arrays.asList(
                    "12121",
                    "21212",
                    "12121",
                    "21212",
                    "12121"
            );

            List<String> pattern3Matrix = Arrays.asList(
                    "00100",
                    "00100",
                    "33333",
                    "00100",
                    "00100"
            );

            List<String> pattern4Matrix = Arrays.asList(
                    "22222",
                    "20002",
                    "20302",
                    "20002",
                    "22222"
            );

            List<String> pattern5Matrix = Arrays.asList(
                    "00100",
                    "02020",
                    "10301",
                    "02020",
                    "00100"
            );

            // Define color palette
            List<String> colorPalette = Arrays.asList("#FF0000", "#0000FF", "#00FF00");

            // Save patterns for arounr
            KnittingPattern pattern1 = new KnittingPattern("Diagonal Stripes", true, pattern1Matrix, colorPalette, arounr);
            pattern1.setImageUrl("http://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169520/o5o94eynzyuf1nwlb8oh.png");
            patternRepository.save(pattern1);

            KnittingPattern pattern2 = new KnittingPattern("Checkerboard", true, pattern2Matrix, colorPalette, arounr);
            pattern2.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169578/dkkhgtz7hmqt6egzywos.png");
            patternRepository.save(pattern2);

            KnittingPattern pattern3 = new KnittingPattern("Cross", true, pattern3Matrix, colorPalette, arounr);
            pattern3.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169634/qcb8zfbcnqnghutilwuk.png");
            patternRepository.save(pattern3);

            KnittingPattern pattern4 = new KnittingPattern("Border", true, pattern4Matrix, colorPalette, arounr);
            pattern4.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169676/yvyi4qyxhdgogklj1qgt.png");
            patternRepository.save(pattern4);

            KnittingPattern pattern5 = new KnittingPattern("Diamond", true, pattern5Matrix, colorPalette, arounr);
            pattern5.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169708/usknwrzonwaaf1ivnto7.png");
            patternRepository.save(pattern5);

            // Save patterns for user1 with unique names and statuses
            KnittingPattern pattern6 = new KnittingPattern("Zigzag Stripes", false, pattern1Matrix, colorPalette, user1);
            pattern6.setImageUrl("http://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169520/o5o94eynzyuf1nwlb8oh.png");
            patternRepository.save(pattern6);

            KnittingPattern pattern7 = new KnittingPattern("Gridlock", true, pattern2Matrix, colorPalette, user1);
            pattern7.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169578/dkkhgtz7hmqt6egzywos.png");
            patternRepository.save(pattern7);

            KnittingPattern pattern8 = new KnittingPattern("Crossroads", false, pattern3Matrix, colorPalette, user1);
            pattern8.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169634/qcb8zfbcnqnghutilwuk.png");
            patternRepository.save(pattern8);

            KnittingPattern pattern9 = new KnittingPattern("Framed Border", true, pattern4Matrix, colorPalette, user1);
            pattern9.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169676/yvyi4qyxhdgogklj1qgt.png");
            patternRepository.save(pattern9);

            KnittingPattern pattern10 = new KnittingPattern("Emerald Diamond", false, pattern5Matrix, colorPalette, user1);
            pattern10.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169708/usknwrzonwaaf1ivnto7.png");
            patternRepository.save(pattern10);

            // Save patterns for user2 with unique names and statuses
            KnittingPattern pattern11 = new KnittingPattern("Striped Angles", true, pattern1Matrix, colorPalette, user2);
            pattern11.setImageUrl("http://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169520/o5o94eynzyuf1nwlb8oh.png");
            patternRepository.save(pattern11);

            KnittingPattern pattern12 = new KnittingPattern("Checkered Dreams", false, pattern2Matrix, colorPalette, user2);
            pattern12.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169578/dkkhgtz7hmqt6egzywos.png");
            patternRepository.save(pattern12);

            KnittingPattern pattern13 = new KnittingPattern("Intersection", true, pattern3Matrix, colorPalette, user2);
            pattern13.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169634/qcb8zfbcnqnghutilwuk.png");
            patternRepository.save(pattern13);

            KnittingPattern pattern14 = new KnittingPattern("Golden Frame", false, pattern4Matrix, colorPalette, user2);
            pattern14.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169676/yvyi4qyxhdgogklj1qgt.png");
            patternRepository.save(pattern14);

            KnittingPattern pattern15 = new KnittingPattern("Gemstone", true, pattern5Matrix, colorPalette, user2);
            pattern15.setImageUrl("https://res.cloudinary.com/dm0wbbn7s/image/upload/v1732169708/usknwrzonwaaf1ivnto7.png");
            patternRepository.save(pattern15);

            System.out.println("Database initialized with users and patterns.");
        };
    }
}
