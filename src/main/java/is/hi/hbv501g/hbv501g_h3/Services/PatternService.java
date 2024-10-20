package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface PatternService {
    // Page<Pattern> findByNamePaginated(String name, int page, int size);
    // Page<Pattern> findAllPatternsPaginated(int page, int size);
    // Page<Pattern> findPublicPatternsPaginated(int page, int size);
    // Page<Pattern> findPublicPatternsPaginatedSortedByName(int page, int size);
    // Page<Pattern> findPublicPatternsPaginatedSortedByDate(int page, int size);
    // Page<Pattern> findPublicPatternsByUsername(String username, int page, int size); //if username contains username
    // Page<Pattern> findPrivatePatternsPaginated(Long ownerID, int page, int size);
    // Page<Pattern> findPrivatePatternsPaginatedSortedByName(Long ownerID, int page, int size);
    // Page<Pattern> findPrivatePatternsPaginatedSortedByDate(Long ownerID, int page, int size);
	Page<Pattern> findPatterns(Long ownerId, String searchTerm, Boolean isPublic, Pageable pageable);
    Pattern findById(Long ID);
    Pattern save(Pattern pattern);
    void delete(Long ID);
    boolean updatePattern(Long ID, Long ownerID,
                          List<Integer> newPatternMatrix,
                          List<String> newColorScheme,
                          String newName,
                          Integer stitches,
                          Integer rows,
                          Integer numberOfColors);
    boolean changePatternName(Long ID, Long ownerID, String newName);
	Pattern patchPattern(Long id, Map<String, Object> updates);
    // boolean makePublic(Long ID, Long ownerID);
    // boolean makePrivate(Long ID, Long ownerID);
}
