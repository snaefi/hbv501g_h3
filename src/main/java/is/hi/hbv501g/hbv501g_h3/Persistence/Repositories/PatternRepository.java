package is.hi.hbv501g.hbv501g_h3.Persistence.Repositories;

import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatternRepository extends JpaRepository<KnittingPattern, Long> {

    @Query("SELECT p FROM KnittingPattern p WHERE " +
            "(:isPublic IS NULL OR p.isPublic = :isPublic) " +
            "AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:username IS NULL OR p.owner.username = :username)")
    Page<KnittingPattern> searchPatterns(Boolean isPublic, String title, String username, Pageable pageable);

    @Query("SELECT p FROM KnittingPattern p " +
            "WHERE p.id IN :patternIds " +
            "AND (p.isPublic = true OR p.owner.id = :userId OR :user MEMBER OF p.collaborators) " +
            "AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:ownerUsername IS NULL OR p.owner.username = :ownerUsername)")
    Page<KnittingPattern> findPatternsByUserLikedPatternIds(
            @Param("patternIds") List<Long> patternIds,
            @Param("userId") Long userId,
            @Param("user") User user, // The full User object for checking collaborators
            @Param("title") String title,
            @Param("ownerUsername") String ownerUsername,
            Pageable pageable);


    @Query("SELECT p FROM KnittingPattern p " +
            "JOIN p.collaborators c " +
            "WHERE c.username = :username " +
            "AND (:title IS NULL OR LOWER(p.title) LIKE LOWER(CONCAT('%', :title, '%'))) " +
            "AND (:ownerUsername IS NULL OR p.owner.username = :ownerUsername)")
    Page<KnittingPattern> findSharedPatternsWithUser(
            @Param("username") String username,
            @Param("title") String title,
            @Param("ownerUsername") String ownerUsername,
            Pageable pageable);
}
