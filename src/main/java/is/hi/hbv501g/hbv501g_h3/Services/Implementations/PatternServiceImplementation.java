package is.hi.hbv501g.hbv501g_h3.Services.Implementations;


import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import net.coobird.thumbnailator.Thumbnails;
import is.hi.hbv501g.hbv501g_h3.Services.ImageUploader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;
import java.net.URL;

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

    @Override
    public String generateImageURL(KnittingPattern pattern) {
        // Generate image
        File patternImage;
        try {
            patternImage = generatePatternThumbnail(pattern);
        } catch (IOException e) {
            throw new RuntimeException("Failed to generate pattern thumbnail", e);
        }

        // Upload image
        String imageURL;
        try {
            imageURL = imageUploader.uploadImage(patternImage);
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload pattern image", e);
        } finally {
            // Ensure the temporary file is deleted after use
            if (patternImage != null && patternImage.exists()) {
                if (!patternImage.delete()) {
                    System.err.println("Warning: Failed to delete temporary file " + patternImage.getAbsolutePath());
                }
            }
        }

        return imageURL;
    }

    @Override
    public KnittingPattern copyPatternForUser(KnittingPattern originalPattern, User user) {
        // Create a copy of the pattern
        KnittingPattern copiedPattern = new KnittingPattern();
        copiedPattern.setTitle(originalPattern.getTitle() + " (Copy)");
        copiedPattern.setIsPublic(false);
        copiedPattern.setPatternMatrix(List.copyOf(originalPattern.getPatternMatrix()));
        copiedPattern.setColorCodes(List.copyOf(originalPattern.getColorCodes()));
        copiedPattern.setImageUrl(originalPattern.getImageUrl());
        copiedPattern.setOwner(user);

        return patternRepository.save(copiedPattern);
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
    public Page<KnittingPattern> getLikedPatternsByUser(User user, String title, String username, Pageable pageable) {
        List<Long> likedPatternIds = user.getLikedPatternIds();
        return patternRepository.findPatternsByUserLikedPatternIds(likedPatternIds, user.getId(), title, username, pageable);
    }

    @Override
    public KnittingPattern updatePattern(KnittingPattern knittingPattern) {
        return patternRepository.save(knittingPattern);
    }

    @Override
    public void deletePattern(Long id) {
        patternRepository.deleteById(id);
    }

	@Override
    public int[][] makeUrlPattern(String url, int width, int numColors) throws IOException {
        // Fetch the image from the provided URL
        BufferedImage originalImage = ImageIO.read(new URL(url));

        // Resize the image while maintaining aspect ratio
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        double aspectRatio = (double) originalHeight / originalWidth;
        int height = (int) Math.round(width * aspectRatio);

        BufferedImage resizedImage = Thumbnails.of(originalImage)
            .size(width, height)
            .asBufferedImage();

        // Convert the image to ARGB to handle transparency
        BufferedImage argbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = argbImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();

        // Prepare the matrix
        int[][] matrix = new int[height][width];

        // Prepare a list to store unique colors
        List<Color> uniqueColors = new ArrayList<>();

        // Iterate over each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = argbImage.getRGB(x, y);
                Color color = new Color(argb, true);

                // if (color.getAlpha() == 0) {
                //     // Transparent pixel
                //     matrix[y][x] = 0;
                // } else {
                    // Add color to the list if not already present
                    if (!containsColor(uniqueColors, color)) {
                        uniqueColors.add(color);
                    }
                    matrix[y][x] = -1; // Placeholder for now
                // }
            }
        }

        // Quantize colors if necessary
        Color[] palette = quantizeColors(uniqueColors, numColors);

        // Map colors to indices
		Map<Integer, Integer> colorToIndex = new HashMap<Integer, Integer>();
        for (int i = 0; i < palette.length; i++) {
            colorToIndex.put(palette[i].getRGB(), i + 1); // Indices start from 1
        }

        // Update the matrix with color indices
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[y][x] == -1) {
                    int argb = argbImage.getRGB(x, y);
                    Color color = new Color(argb, true);
                    int nearestColorIndex = findNearestColorIndex(color, palette);
                    matrix[y][x] = nearestColorIndex + 1; // Indices start from 1
                }
            }
        }

        return matrix;
    }

    // Helper methods
    private boolean containsColor(List<Color> colorList, Color color) {
        for (Color c : colorList) {
            if (c.getRGB() == color.getRGB()) {
                return true;
            }
        }
        return false;
    }

    private Color[] quantizeColors(List<Color> colors, int numColors) {
        // If the number of unique colors is less than or equal to numColors, use them directly
        if (colors.size() <= numColors) {
            return colors.toArray(new Color[0]);
        }

        // Use K-Means clustering for color quantization
        Color[] centroids = new Color[numColors];
        Random random = new Random();

        // Initialize centroids randomly
        for (int i = 0; i < numColors; i++) {
            centroids[i] = colors.get(random.nextInt(colors.size()));
        }

        boolean centroidsChanged;
        int[] assignments = new int[colors.size()];

        // Repeat until convergence
        do {
            centroidsChanged = false;

            // Assign colors to the nearest centroid
            for (int i = 0; i < colors.size(); i++) {
                Color color = colors.get(i);
                int nearestCentroidIndex = findNearestColorIndex(color, centroids);
                assignments[i] = nearestCentroidIndex;
            }

            // Update centroids
            for (int i = 0; i < numColors; i++) {
                int count = 0;
                int sumR = 0, sumG = 0, sumB = 0;

                for (int j = 0; j < colors.size(); j++) {
                    if (assignments[j] == i) {
                        Color color = colors.get(j);
                        sumR += color.getRed();
                        sumG += color.getGreen();
                        sumB += color.getBlue();
                        count++;
                    }
                }

                if (count > 0) {
                    Color newCentroid = new Color(sumR / count, sumG / count, sumB / count);
                    if (!colorsEqual(centroids[i], newCentroid)) {
                        centroidsChanged = true;
                        centroids[i] = newCentroid;
                    }
                }
            }
        } while (centroidsChanged);

        return centroids;
    }

    private int findNearestColorIndex(Color color, Color[] palette) {
        int nearestIndex = 0;
        double minDistance = colorDistance(color, palette[0]);

        for (int i = 1; i < palette.length; i++) {
            double distance = colorDistance(color, palette[i]);
            if (distance < minDistance) {
                minDistance = distance;
                nearestIndex = i;
            }
        }
        return nearestIndex;
    }

    private double colorDistance(Color c1, Color c2) {
        int rDiff = c1.getRed() - c2.getRed();
        int gDiff = c1.getGreen() - c2.getGreen();
        int bDiff = c1.getBlue() - c2.getBlue();
        return Math.sqrt(rDiff * rDiff + gDiff * gDiff + bDiff * bDiff);
    }

    private boolean colorsEqual(Color c1, Color c2) {
        return c1.getRed() == c2.getRed() && c1.getGreen() == c2.getGreen() && c1.getBlue() == c2.getBlue();
    }
	public int[][] makeFilePattern(MultipartFile file, int width, int numColors) throws IOException {
        // Read the image from the MultipartFile
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // Validate that the image was read successfully
        if (originalImage == null) {
            throw new IOException("Failed to read image from the uploaded file.");
        }

        // Resize the image while maintaining aspect ratio
        int originalWidth = originalImage.getWidth();
        int originalHeight = originalImage.getHeight();
        double aspectRatio = (double) originalHeight / originalWidth;
        int height = (int) Math.round(width * aspectRatio);

        BufferedImage resizedImage = Thumbnails.of(originalImage)
            .size(width, height)
            .asBufferedImage();

        // Convert the image to ARGB to handle transparency
        BufferedImage argbImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = argbImage.createGraphics();
        g2d.drawImage(resizedImage, 0, 0, null);
        g2d.dispose();

        // Prepare the matrix
        int[][] matrix = new int[height][width];

        // Prepare a list to store unique colors
        List<Color> uniqueColors = new ArrayList<>();

        // Iterate over each pixel
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int argb = argbImage.getRGB(x, y);
                Color color = new Color(argb, true);

                // Add color to the list if not already present
                if (!containsColor(uniqueColors, color)) {
                    uniqueColors.add(color);
                }
                matrix[y][x] = -1; // Placeholder for now
            }
        }

        // Quantize colors if necessary
        Color[] palette = quantizeColors(uniqueColors, numColors);

        // Map colors to indices
        Map<Integer, Integer> colorToIndex = new HashMap<>();
        for (int i = 0; i < palette.length; i++) {
            colorToIndex.put(palette[i].getRGB(), i + 1); // Indices start from 1
        }

        // Update the matrix with color indices
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (matrix[y][x] == -1) {
                    int argb = argbImage.getRGB(x, y);
                    Color color = new Color(argb, true);
                    int nearestColorIndex = findNearestColorIndex(color, palette);
                    matrix[y][x] = nearestColorIndex + 1; // Indices start from 1
                }
            }
        }

        return matrix;
    }
}