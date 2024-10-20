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
    Page<Pattern> findByOwnerUsernameContainingAndIsPublicTrue(String username,Pageable pageable);

	    // Search patterns by name (case-insensitive) and public status
		Page<Pattern> findByIsPublicAndNameContainingIgnoreCase(Boolean isPublic, String name, Pageable pageable);

		// Find patterns by owner ID and public status
		Page<Pattern> findByOwner_IDAndIsPublic(Long ownerId, Boolean isPublic, Pageable pageable);
	
		// Find patterns by owner ID, public status, and name
		Page<Pattern> findByOwner_IDAndIsPublicAndNameContainingIgnoreCase(Long ownerID, Boolean isPublic, String name, Pageable pageable);
	
		// Optional: Find patterns by public status only
		Page<Pattern> findByIsPublic(Boolean isPublic, Pageable pageable);
}
