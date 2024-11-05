package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PatternService {
    Optional<KnittingPattern> getPatternById(Long id);
    KnittingPattern savePattern(KnittingPattern pattern);
    Page<KnittingPattern> getPatterns(Boolean isPublic, String title, String username, Pageable pageable);
    KnittingPattern updatePattern(KnittingPattern knittingPattern);
    void likePattern(User user, KnittingPattern pattern);
    void unlikePattern(User user, KnittingPattern pattern);
    void deletePattern(Long id);
}
