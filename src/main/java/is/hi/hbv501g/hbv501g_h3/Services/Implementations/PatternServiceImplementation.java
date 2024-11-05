package is.hi.hbv501g.hbv501g_h3.Services.Implementations;


import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.UserRepository;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;

import java.util.Optional;

@Service
public class PatternServiceImplementation implements PatternService {

    @Autowired
    private PatternRepository patternRepository;

    @Autowired
    private UserRepository userRepository;


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