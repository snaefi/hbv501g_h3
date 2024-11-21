package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Events.PatternSharedEvent;
import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.AuthenticationService;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import is.hi.hbv501g.hbv501g_h3.dto.PatternRequest;
import jakarta.validation.Valid;
import net.coobird.thumbnailator.Thumbnails;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.security.InvalidParameterException;
import java.util.*;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.URL;
import javax.imageio.ImageIO;

@RestController
@RequestMapping("/patterns")
@CrossOrigin(origins = "*")
public class PatternController {
    @Autowired
    private PatternService patternService;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ApplicationEventPublisher eventPublisher;


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
    @GetMapping("/private")
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
        return getAllPatterns(null, title, user.getUsername(), sortBy, direction, pageable);
    }

    @PostMapping("/share/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void sharePattern(@PathVariable Long id,
                             @RequestHeader(value = "Authorization") String authorizationHeader,
                             @RequestBody Map<String, String> request) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);
        User user = authenticationService.getProfile(token);

        String targetUsername = request.get("username");

        if (Objects.equals(targetUsername, user.getUsername())) {
            throw new InvalidParameterException("Can't share with yourself!");
        }

        if (targetUsername == null || targetUsername.isBlank()) {
            throw new InvalidParameterException("Username must be provided");
        }

        KnittingPattern pattern = patternService.getPatternById(id).orElseThrow(() -> new ApiExceptions.PatternNotFoundException(id));

        // Check if user owns pattern being shared
        if (!Objects.equals(pattern.getOwnerUsername(), user.getUsername())) {
            throw new ApiExceptions.NotAuthorizedException();
        }

        // Get user for receiving the notification
        User targetUser = userService.getUserByUsername(targetUsername)
                .orElseThrow(() -> new ApiExceptions.UserNotFoundException(targetUsername));

        PatternSharedEvent event = new PatternSharedEvent(pattern, targetUser);
        eventPublisher.publishEvent(event);
    }

    @PostMapping("/save/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void savePatternById(@RequestHeader(value = "Authorization") String authorizationHeader,
                                           @PathVariable Long id) {
        // Validate and extract user from token for private patterns
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new ApiExceptions.UserInvalidAccess("You are not authorized.");
        }

        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        User user = authenticationService.getProfile(token);

        // Fetch pattern
        KnittingPattern pattern = patternService.getPatternById(id)
                .orElseThrow(() -> new ApiExceptions.PatternNotFoundException(id));

        // Use the service to copy the pattern for the authenticated user
        patternService.copyPatternForUser(pattern, user);
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

        boolean isOwner = pattern.getOwnerUsername().equals(user.getUsername());
        boolean isCollaborator = pattern.getCollaboratorUsernames().contains(user.getUsername());

        if (!isOwner && !isCollaborator) {
            throw new ApiExceptions.UserInvalidAccess("You are not authorized to view this pattern.");
        }

        // Return the pattern if it belongs to the authenticated user
        return pattern;
    }

    // Create pattern
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
 public ResponseEntity<KnittingPattern> createPattern(
        @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
        @Valid @RequestBody KnittingPattern knittingPattern
    ) {
        // Validate Authorization header
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        // Extract token and get user profile
        String token = authorizationHeader.substring(7); // Remove "Bearer " prefix
        User owner = authenticationService.getProfile(token);

        // Generate image URL based on the pattern
        String imageURL = patternService.generateImageURL(knittingPattern);
        knittingPattern.setImageUrl(imageURL);
        knittingPattern.setOwner(owner);

        // Save and return the created pattern
        KnittingPattern savedPattern = patternService.savePattern(knittingPattern);

        // Optionally include a `Location` header pointing to the created resource
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(savedPattern.getId())
                .toUri();

        return ResponseEntity.created(location).body(savedPattern);
    }

    @PostMapping("/url")
    public ResponseEntity<int[][]> generatePattern(@RequestBody PatternRequest request) {
        String imageUrl = request.getUrl();
        int width = request.getWidth();
        int numColors = request.getNumColors();

        if (imageUrl == null || imageUrl.isEmpty() || width <= 0 || numColors <= 0) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            int[][] matrix = patternService.makeUrlPattern(imageUrl, width, numColors);
            return ResponseEntity.ok(matrix);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }
    }

	@PostMapping("/file")
    public ResponseEntity<int[][]> generatePatternFromFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam("width") int width,
            @RequestParam("numColors") int numColors) {

        if (file.isEmpty() || width <= 0 || numColors <= 0) {
            return ResponseEntity.badRequest().build();
        }

        try {
            int[][] matrix = patternService.makeFilePattern(file, width, numColors);
            return ResponseEntity.ok(matrix);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
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
                case "colorCodes":
                    List<String> colorCodes = (List<String>) value;
                    existingPattern.setColorCodes(colorCodes);
                case "patternMatrix":
                    List<String> patternMatrix = (List<String>) value;
                    existingPattern.setPatternMatrix(patternMatrix);
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



