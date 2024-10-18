package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;
import java.util.List;

public interface PatternService {
    List<Pattern> findByName(String name);
    List<Pattern> findAll();
    Pattern findById(Long ID);
    Pattern save(Pattern pattern);
    void delete(Pattern pattern);
    boolean updatePattern(Long ID, Long ownerID,
                          List<Integer> newPatternMatrix,
                          List<String> newColorScheme,
                          String newName,
                          Integer stitches,
                          Integer rows,
                          Integer numberOfColors);
    boolean changePatternName(Long ID, Long ownerID, String newName);
    boolean makePublic(Long ID, Long ownerID);
    boolean makePrivate(Long ID, Long ownerID);
}
