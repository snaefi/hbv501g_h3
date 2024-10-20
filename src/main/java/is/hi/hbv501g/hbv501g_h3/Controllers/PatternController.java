package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/patterns")
public class PatternController {

    @Autowired
    private PatternService patternService;

    @Autowired
    private UserService userService;

    @GetMapping
    public Page<KnittingPattern> getAllPatterns(
            @RequestParam(value = "public", required = false) Boolean isPublic,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @PageableDefault(size = 8) Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return patternService.getPatterns(isPublic, title, username, sortedPageable);
    }

    // Endpoint to get a Pattern by ID
    @GetMapping("/{id}")
    public KnittingPattern getPatternById(@PathVariable Long id) {
        System.out.println("Entering getPatternById method with ID: " + id);

        return patternService.getPatternById(id)
                .orElseThrow(() -> new ApiExceptions.PatternNotFoundException(id));
    }

    // Create pattern
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KnittingPattern createPattern(@Valid @RequestBody KnittingPattern knittingPattern) {
        // Verify if the user exists and assign the owner to the pattern (owner will later be assigned via token from user)
        User owner = userService.getUserById(knittingPattern.getOwner().getId())
                .orElseThrow(() -> new ApiExceptions.UserNotFoundException(knittingPattern.getOwner().getId()));

        knittingPattern.setOwner(owner);

        return patternService.savePattern(knittingPattern);
    }

    @PatchMapping("/{id}")
    public KnittingPattern patchPattern(
            @PathVariable Long id,
            @RequestBody Map<String, Object> updates) {

        KnittingPattern existingPattern = patternService.getPatternById(id)
                .orElseThrow(() -> new ApiExceptions.PatternNotFoundException(id));

        updates.forEach((field, value) -> {
            switch (field) {
                case "title":
                    existingPattern.setTitle((String) value);
                    break;
                case "isPublic":
                    existingPattern.setIsPublic((Boolean) value);
                    break;
                case "patternMatrix":
                    existingPattern.setPatternMatrix((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
        });

        // Save the updated pattern
        return patternService.updatePattern(existingPattern);
    }

    // Delete a pattern by ID
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)  // 204 No Content
    public void deletePattern(@PathVariable Long id) {
        // Ensure the pattern exists before deletion
        patternService.getPatternById(id)
                .orElseThrow(() -> new ApiExceptions.PatternNotFoundException(id));

        patternService.deletePattern(id);
    }

}



