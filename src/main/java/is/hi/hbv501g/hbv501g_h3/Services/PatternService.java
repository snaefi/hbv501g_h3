package is.hi.hbv501g.hbv501g_h3.Services;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;
import java.util.List;

public interface PatternService {
    Pattern findByName(String name);
    List<Pattern> findAll();
    Pattern findById(Long ID);
    Pattern save(Pattern pattern);
    void delete(Pattern pattern);
}
