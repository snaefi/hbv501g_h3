package is.hi.hbv501g.hbv501g_h3.Controllers;

import is.hi.hbv501g.hbv501g_h3.Exceptions.ApiExceptions;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.KnittingPattern;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.Notification;
import is.hi.hbv501g.hbv501g_h3.Persistence.Entities.User;
import is.hi.hbv501g.hbv501g_h3.Services.AuthenticationService;
import is.hi.hbv501g.hbv501g_h3.Services.PatternService;
import is.hi.hbv501g.hbv501g_h3.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    PatternService patternService;

    @Autowired
    AuthenticationService authenticationService;


    // Endpoint to get a User by ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        return userService.getUserById(id)
                .orElseThrow(() -> new ApiExceptions.UserNotFoundException(id));
    }

    @GetMapping("/username/{username}")
    public User getUserByUsername(@PathVariable String username) {
        return userService.getUserByUsername(username)
                .orElseThrow(() -> new ApiExceptions.UserNotFoundException(username));
    }

    @GetMapping("/notifications")
    public List<Notification> getUserNotifications(@RequestHeader(value = "Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User user = authenticationService.getProfile(token);
        return userService.getUserNotifications(user);
    }

    @PostMapping("/notifications/{id}/accept")
    @ResponseStatus(HttpStatus.OK)
    public void acceptNotification(@PathVariable Long id,
                                                 @RequestHeader(value = "Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User user = authenticationService.getProfile(token);

        userService.acceptNotification(user, id);
    }

    @PostMapping("/notifications/{id}/decline")
    @ResponseStatus(HttpStatus.OK)
    public void declineNotification(@PathVariable Long id,
                                                 @RequestHeader(value = "Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User user = authenticationService.getProfile(token);

        userService.declineNotification(user, id);
    }


    @GetMapping
    public Page<User> getAllUsers(
            @RequestParam(value = "username", required = false) String username,
            @PageableDefault(size = 8) Pageable pageable
    ) {
        return userService.getAllUsers(username, pageable);
    }

    @GetMapping("/likedPatterns")
    public Page<KnittingPattern> getLikedPatterns(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @PageableDefault(size = 8) Pageable pageable) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User user = authenticationService.getProfile(token);

        // Apply sorting based on parameters
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return patternService.getLikedPatternsByUser(user, title, username, sortedPageable);
    }

    @GetMapping("/sharedPatterns")
    public Page<KnittingPattern> getSharedPatterns(
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "username", required = false) String username,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id") String sortBy,
            @RequestParam(value = "direction", required = false, defaultValue = "asc") String direction,
            @PageableDefault(size = 8) Pageable pageable) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User user = authenticationService.getProfile(token);

        // Apply sorting based on parameters
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), sort);

        return patternService.getSharedPatternsWithUser(user, title, username, sortedPageable);
    }

    // Create a new user
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@Valid @RequestBody User user) {
        return userService.createUser(user);
    }

    // Delete a user by ID
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@RequestHeader(value = "Authorization") String authorizationHeader) {
        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User existingUser = authenticationService.getProfile(token);

        userService.deleteUser(existingUser.getId());
    }

    private boolean isPasswordValid(String password) {
        String passwordRegex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
        return password != null && !password.isEmpty() && Pattern.matches(passwordRegex, password);
    }

    // Patch user
    @PatchMapping
    public User patchUser(@RequestHeader(value = "Authorization") String authorizationHeader, @RequestBody Map<String, Object> updates) {

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new IllegalArgumentException("Invalid Authorization header");
        }

        String token = authorizationHeader.substring(7);

        // Authenticate the user with the token
        User existingUser = authenticationService.getProfile(token);

        updates.forEach((field, value) -> {
            switch (field) {
                case "username":
                    existingUser.setUsername((String) value);
                    break;
                case "password":
                    // very bad
                    if (!isPasswordValid((String) value)) {
                        throw new IllegalArgumentException(
                                "Invalid password: Password must contain at least one digit, one lowercase letter, " +
                                        "one uppercase letter, one special character (@#$%^&+=!), and be at least 8 characters long"
                        );
                    }
                    existingUser.setPassword((String) value);
                    break;
                default:
                    throw new IllegalArgumentException("Invalid field: " + field);
            }
        });

        return userService.updateUser(existingUser);
    }
}
