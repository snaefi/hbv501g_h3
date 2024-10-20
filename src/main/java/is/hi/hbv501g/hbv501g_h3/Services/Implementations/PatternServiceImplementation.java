package is.hi.hbv501g.hbv501g_h3.Services.Implementations;


import is.hi.hbv501g.hbv501g_h3.Persistence.Repositories.PatternRepository;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Pattern;


import java.util.*;

@Service
public class PatternServiceImplementation implements PatternService {
    private PatternRepository patternRepository;

    @Autowired //for our constructor
    public PatternServiceImplementation(PatternRepository patternRepository) {
        this.patternRepository = patternRepository;
    }

	@Override
    public Page<Pattern> findPatterns(Long ownerId, String searchTerm, Boolean isPublic, Pageable pageable) {
		if (ownerId == null && (searchTerm == null || searchTerm.isEmpty()) && isPublic == null) {
			return patternRepository.findAll(pageable); // Return all patterns
		}
        if (ownerId != null) {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return patternRepository.findByOwner_IDAndIsPublicAndNameContainingIgnoreCase(ownerId, isPublic, searchTerm, pageable);
            } else {
                return patternRepository.findByOwner_IDAndIsPublic(ownerId, isPublic, pageable);
            }
        } else {
            if (searchTerm != null && !searchTerm.isEmpty()) {
                return patternRepository.findByIsPublicAndNameContainingIgnoreCase(isPublic, searchTerm, pageable);
            } else {
                return patternRepository.findByIsPublic(isPublic, pageable);
            }
        }
    }

    public Pattern patchPattern(Long id, Map<String, Object> updates) {
        Optional<Pattern> existingPattern = patternRepository.findById(id);

        if (existingPattern.isEmpty()) {
            throw new NoSuchElementException("Pattern with id=" + id + " not found");
        }

        Pattern pattern = existingPattern.get();
        Map<String, String> errors = new HashMap<>();

        // Validate and update the provided fields only
        if (updates.containsKey("name")) {
            String name = (String) updates.get("name");
            if (name == null || name.isBlank()) {
                errors.put("name", "Name cannot be blank");
            } else {
                pattern.setName(name);
            }
        }

        if (updates.containsKey("patternMatrix")) {
            List<Integer> patternMatrix = (List<Integer>) updates.get("patternMatrix");
            if (patternMatrix == null || patternMatrix.isEmpty()) {
                errors.put("patternMatrix", "Pattern matrix is required and cannot be empty");
            } else {
                pattern.setPatternMatrix(patternMatrix);
            }
        }

        if (updates.containsKey("rows")) {
            Integer rows = (Integer) updates.get("rows");
            if (rows == null || rows < 1) {
                errors.put("rows", "Rows must be at least 1");
            } else {
                pattern.setRows(rows);
            }
        }

        if (updates.containsKey("stitches")) {
            Integer stitches = (Integer) updates.get("stitches");
            if (stitches == null || stitches < 1) {
                errors.put("stitches", "Stitches must be at least 1");
            } else {
                pattern.setStitches(stitches);
            }
        }

        if (updates.containsKey("numberOfColors")) {
            Integer numberOfColors = (Integer) updates.get("numberOfColors");
            if (numberOfColors == null || numberOfColors < 1) {
                errors.put("numberOfColors", "There must be at least 1 color");
            } else {
                pattern.setNumberOfColors(numberOfColors);
            }
        }

        if (updates.containsKey("colorScheme")) {
            List<String> colorScheme = (List<String>) updates.get("colorScheme");
            if (colorScheme == null || colorScheme.isEmpty()) {
                errors.put("colorScheme", "Color scheme is required and cannot be empty");
            } else {
                pattern.setColorScheme(colorScheme);
            }
        }

        if (updates.containsKey("isPublic")) {
            Boolean isPublic = (Boolean) updates.get("isPublic");
            if (isPublic == null) {
                errors.put("isPublic", "Public status must be a boolean value");
            } else {
                pattern.setPublic(isPublic);
            }
        }

        // If there are validation errors, throw an exception or handle it
        if (!errors.isEmpty()) {
            throw new IllegalArgumentException("Validation failed: " + errors);
        }

        // Update modification date
        pattern.setModificationDate(new Date());

        return patternRepository.save(pattern); // Save the updated pattern
    }

    public void deleteById(Long id) {
        Optional<Pattern> pattern = patternRepository.findById(id);
        if (pattern.isEmpty()) {
            throw new NoSuchElementException("Pattern with id " + id + " not found");
        }
        patternRepository.deleteById(id);
    }
    // @Override
    // public Page<Pattern> findByNamePaginated(String name, int page, int size){
    //     Pageable pageable = PageRequest.of(page, size);
    //     return patternRepository.findByName(name, pageable);
    // }


    // @Override
    // public Page<Pattern> findAllPatternsPaginated(int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size);
    //     return patternRepository.findAll(pageable);
    // }

    // @Override
    // public Page<Pattern> findPublicPatternsPaginated(int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size);
    //     return patternRepository.findByIsPublicTrue(pageable);
    // }

    // @Override
    // public  Page<Pattern> findPublicPatternsPaginatedSortedByName(int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
    //     return patternRepository.findByIsPublicTrue(pageable);
    // }

    // @Override
    // public Page<Pattern> findPublicPatternsPaginatedSortedByDate(int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size, Sort.by("date"));
    //     return patternRepository.findByIsPublicTrue(pageable);
    // }

    // @Override
    // public Page<Pattern> findPublicPatternsByUsername(String username, int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size);
    //     return patternRepository.findByOwnerUsernameContainingAndIsPublicTrue(username,pageable);
    // }

    // @Override
    // public Page<Pattern> findPrivatePatternsPaginated(Long ownerID, int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size);
    //     return patternRepository.findByOwnerIDAndIsPublicFalse(ownerID, pageable);
    // }

    // @Override
    // public Page<Pattern> findPrivatePatternsPaginatedSortedByName(Long ownerID, int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size, Sort.by("name"));
    //     return patternRepository.findByOwnerIDAndIsPublicFalse(ownerID, pageable);
    // }

    // @Override
    // public Page<Pattern> findPrivatePatternsPaginatedSortedByDate(Long ownerID, int page, int size) {
    //     Pageable pageable = PageRequest.of(page, size, Sort.by("date"));
    //     return patternRepository.findByOwnerIDAndIsPublicFalse(ownerID, pageable);
    // }

    @Override
    public Pattern findById(Long id) {
        return patternRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Pattern with id=" + id + " not found"));
    }

    @Override
    public Pattern save(Pattern pattern){
        return patternRepository.save(pattern);
    }

    @Override
    public boolean updatePattern(Long ID, Long ownerID,
                                 List<Integer> newPatternMatrix,
                                 List<String> newColorScheme,
                                 String newName,
                                 Integer stitches,
                                 Integer rows,
                                 Integer numberOfColors) {
        Optional<Pattern> optionalPattern = patternRepository.findByIDAndOwnerID(ID, ownerID);
        if (optionalPattern.isPresent()) {
            Pattern pattern = optionalPattern.get();

            if (newPatternMatrix != null) {
                pattern.setPatternMatrix(newPatternMatrix);
            }

            if (newColorScheme != null) {
                pattern.setColorScheme(newColorScheme);
            }

            if (newName != null) {
                pattern.setName(newName);
            }
            pattern.setModificationDate(new Date());
            patternRepository.save(pattern);
            return true;
        }
        return false;
    }


    @Override
    public boolean changePatternName(Long ID, Long ownerID, String newName) {
        Pattern pattern = patternRepository.findByIDAndOwnerID(ID,ownerID).orElse(null);
        if(pattern != null){
            pattern.setName(newName);
            pattern.setModificationDate(new Date());
            patternRepository.save(pattern);
            return true;
        }
        return false;
    }

    // @Override
    // public boolean makePublic(Long ID, Long ownerID){
    //     Pattern pattern = patternRepository.findByIDAndOwnerID(ID,ownerID).orElse(null);
    //     if(pattern != null){
    //         pattern.setIsPublic(true);
    //         //Viljum við updatea last modification hér?
    //         patternRepository.save(pattern);
    //         return true;
    //     }
    //     return false;
    // }

    // @Override
    // public boolean makePrivate(Long ID, Long ownerID) {
    //     Pattern pattern = patternRepository.findByIDAndOwnerID(ID,ownerID).orElse(null);
    //     if (pattern != null) {
    //         pattern.setIsPublic(false);
    //         patternRepository.save(pattern);
    //         return true;
    //     }
    //     return false;
    // }

}
