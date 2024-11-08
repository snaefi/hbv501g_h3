package is.hi.hbv501g.hbv501g_h3.Services.Implementations;


import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import javax.imageio.ImageIO;

import java.util.Optional;

@Service
public class PatternServiceImplementation implements PatternService {

    @Autowired
    private PatternRepository patternRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageUploader imageUploader;


    @Override
    public Optional<KnittingPattern> getPatternById(Long id) {
        return patternRepository.findById(id);
    }

    @Override
    public KnittingPattern savePattern(KnittingPattern pattern) {
        return patternRepository.save(pattern);
    }

    @Override
    public void likePattern(User user, KnittingPattern pattern) {
        // Check if the user has not already liked the pattern
        if (user.likePattern(pattern.getId())) {
            pattern.incrementLikeCount();
            patternRepository.save(pattern);
            userRepository.save(user);
        }
    }

    @Override
    public void unlikePattern(User user, KnittingPattern pattern) {
        // Check if the user has liked the pattern
        if (user.unlikePattern(pattern.getId())) {
            pattern.decrementLikeCount();
            patternRepository.save(pattern);
            userRepository.save(user);
        }
    }

    public String generateImageURL(KnittingPattern pattern) {
        // Generate image
        File patternImage;
        try {
            patternImage = generatePatternThumbnail(pattern);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Upload image
        String imageURL;
        try {
            imageURL = imageUploader.uploadImage(patternImage);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return imageURL;
    }
    private File generatePatternThumbnail(KnittingPattern pattern) throws IOException {
        List<String> patternMatrix = pattern.getPatternMatrix();
        List<String> colorCodes = pattern.getColorCodes();

        int height = patternMatrix.size(); // Number of rows
        int width = patternMatrix.getFirst().length(); // Number of columns in the first row string

        // Create a new BufferedImage with the size based on the pattern matrix
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        // Iterate over each row in the pattern matrix
        for (int y = 0; y < height; y++) {
            String row = patternMatrix.get(y);
            for (int x = 0; x < width; x++) {
                int colorIndex = Character.getNumericValue(row.charAt(x)); // Get color index as integer
                if (colorIndex >= 0 && colorIndex < colorCodes.size()) { // Ensure index is within colorCodes bounds
                    String hexColor = colorCodes.get(colorIndex); // Get the hex color code
                    Color color = Color.decode(hexColor); // Convert hex color to Color object
                    image.setRGB(x, y, color.getRGB()); // Set pixel color in image
                }
            }
        }

        // Create a temporary file to store the generated image
        File outputFile = File.createTempFile("knitting_pattern_thumbnail", ".png");
        ImageIO.write(image, "png", outputFile);

        return outputFile;
    }


    @Override
    public Page<KnittingPattern> getPatterns(Boolean isPublic, String title, String username, Pageable pageable) {
        return patternRepository.searchPatterns(isPublic, title, username, pageable);
    }

    @Override
    public KnittingPattern updatePattern(KnittingPattern knittingPattern) {
        return patternRepository.save(knittingPattern);
    }

    @Override
    public void deletePattern(Long id) {
        patternRepository.deleteById(id);
    }
}