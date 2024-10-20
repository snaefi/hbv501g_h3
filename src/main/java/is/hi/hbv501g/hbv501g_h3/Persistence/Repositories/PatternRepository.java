package is.hi.hbv501g.hbv501g_h3.Persistence.Repositories;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PatternRepository extends JpaRepository<KnittingPattern, Long> {

    @Query("SELECT p FROM KnittingPattern p WHERE " +
            "(:isPublic IS NULL OR p.isPublic = :isPublic) " +
            "AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:username IS NULL OR p.owner.username = :username)")
    Page<KnittingPattern> searchPatterns(Boolean isPublic, String title, String username, Pageable pageable);
}
