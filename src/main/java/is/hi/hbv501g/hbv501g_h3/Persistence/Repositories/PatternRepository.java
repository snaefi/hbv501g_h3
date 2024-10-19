package is.hi.hbv501g.hbv501g_h3.Persistence.Repositories;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PatternRepository extends JpaRepository<Pattern, Long> {
    Pattern save(Pattern pattern);
    void delete(Pattern pattern);

    Page<Pattern> findAll(Pageable pageable);
    Page<Pattern> findByName(String name, Pageable pageable);
    //Pattern findById(Long ID);
    Optional<Pattern> findByIDAndOwnerID(long ID, long ownerID);
    Page<Pattern> findByIsPublicTrue(Pageable pageable);
    Page<Pattern> findByOwnerIDAndIsPublicFalse(Long ownerID, Pageable pageable);
}
