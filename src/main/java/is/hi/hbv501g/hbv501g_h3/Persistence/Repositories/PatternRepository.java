package is.hi.hbv501g.hbv501g_h3.Persistence.Repositories;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PatternRepository extends JpaRepository<Pattern, Long> {
    Pattern save(Pattern pattern);
    void delete(Pattern pattern);

    List<Pattern> findAll();
    List<Pattern> findByName(String name);
    //Pattern findById(Long ID);
}
