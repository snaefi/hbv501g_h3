package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.AuthenticationService;
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
import java.util.Optional;


@RestController
@RequestMapping("/patterns")
public class PatternController {
    @Autowired
    private PatternService patternService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;


    // Helper function to get patterns with customizable parameters
    private Page<KnittingPattern> getAllPatterns(
            Boolean isPublic, String title, String username, String sortBy, String direction, Pageable pageable) {

        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return patternService.getPatterns(isPublic, title, username, sortedPageable);
    }

    // Public patterns endpoint, allows filtering by title and username
    @GetMapping("/public")
    public Page<KnittingPattern> getPublicPatterns(
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @PageableDefault(size = 8) Pageable pageable) {

        // Calls getAllPatterns with isPublic set to true
        return getAllPatterns(true, title, username, sortBy, direction, pageable);
    }

    // Private patterns of the authenticated user
    @GetMapping("/user/private")
    public Page<KnittingPattern> getUserPrivatePatterns(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @PageableDefault(size = 8) Pageable pageable) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        User user = authenticationService.getProfile(token);

        // Call getAllPatterns with isPublic = false and the authenticated user's username
        return getAllPatterns(false, title, user.getUsername(), sortBy, direction, pageable);
    }

    // Endpoint to get a Pattern by ID
    @GetMapping("/{id}")
    public KnittingPattern getPatternById(@RequestHeader(value = "Authorization", required = false) String authorizationHeader,
                                          @PathVariable Long id) {
        // Fetch pattern
        KnittingPattern pattern = patternService.getPatternById(id)
                .orElseThrow(() -> new ApiExceptions.PatternNotFoundException(id));

        // If pattern is public, allow access without requiring authentication
        if (pattern.getIsPublic()) {
            return pattern;
        }

        // Validate and extract user from token for private patterns
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ApiExceptions.UserInvalidAccess("You are not authorized to view this pattern.");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        User user = authenticationService.getProfile(token);

        // Check if the authenticated user owns the pattern
        if (!pattern.getOwnerUsername().equals(user.getUsername())) {
            throw new ApiExceptions.UserInvalidAccess("You are not authorized to view this pattern.");
        }

        // Return the pattern if it belongs to the authenticated user
        return pattern;
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

    @PostMapping("/like/{id}")
    public void likePattern(
            @PathVariable Long id,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {

        // Validate Authorization header
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        // Extract token and get user profile
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        User user = authenticationService.getProfile(token);

        // Fetch the pattern
        KnittingPattern pattern = patternService.getPatternById(id)
                .orElseThrow(() -> new ApiExceptions.PatternNotFoundException(id));

        if (user.hasLikedPattern(pattern.getId())) {
            // User has liked the pattern, so we "unlike" it
            patternService.unlikePattern(user, pattern);
        } else {
            // User has not liked the pattern, so we "like" it
            patternService.likePattern(user, pattern);
        }
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



