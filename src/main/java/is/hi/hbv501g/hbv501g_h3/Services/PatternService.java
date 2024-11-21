package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface PatternService {
    Optional<KnittingPattern> getPatternById(Long id);
    KnittingPattern savePattern(KnittingPattern pattern);
    Page<KnittingPattern> getPatterns(Boolean isPublic, String title, String username, Pageable pageable);
    Page<KnittingPattern> getLikedPatternsByUser(User user, String title, String username, Pageable pageable);
    KnittingPattern updatePattern(KnittingPattern knittingPattern);
    String generateImageURL(KnittingPattern pattern);
    KnittingPattern copyPatternForUser(KnittingPattern originalPattern, User user);
    void likePattern(User user, KnittingPattern pattern);
    void unlikePattern(User user, KnittingPattern pattern);
    void deletePattern(Long id);
	int[][] makeUrlPattern(String url,int width,int numColors) throws IOException;
	int[][] makeFilePattern(MultipartFile file, int width, int numColors) throws IOException;
    int[][] knittingPatternMatrixToMatrix(KnittingPattern knittingPattern);
    List<String> matrixToKnittingPatternMatrix(int[][] matrix);
    KnittingPattern addBackground(
            KnittingPattern knittingPattern,
            KnittingPattern backgroundPattern,
            int matrixBackgroundColor,
            boolean border,
            int borderColor,
            int[] backgroundColors
    );

}
